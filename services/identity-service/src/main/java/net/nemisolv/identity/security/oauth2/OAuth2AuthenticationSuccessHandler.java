package net.nemisolv.identity.security.oauth2;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.helper.UserHelper;
import net.nemisolv.identity.payload.profile.CreateOrUpdateUserProfile;
import net.nemisolv.identity.payload.profile.CreateProfileRequest;
import net.nemisolv.identity.payload.profile.UpdateProfileRequest;
import net.nemisolv.identity.repository.RoleRepository;
import net.nemisolv.identity.repository.UserRepository;
import net.nemisolv.identity.repository.http.UserProfileClient;
import net.nemisolv.identity.security.UserPrincipal;
import net.nemisolv.identity.security.oauth2.user.OAuth2UserInfo;
import net.nemisolv.identity.security.oauth2.user.OAuth2UserInfoFactory;
import net.nemisolv.identity.service.AuthService;
import net.nemisolv.identity.service.JwtService;
import net.nemisolv.lib.core._enum.AuthProvider;
import net.nemisolv.lib.core._enum.RoleName;
import net.nemisolv.lib.core.exception.BadRequestException;
import net.nemisolv.identity.properties.AppProperties;
import net.nemisolv.lib.core.exception.ResourceNotFoundException;
import net.nemisolv.lib.util.CookieUtil;
import net.nemisolv.lib.util.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final AppProperties appProperties;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserHelper userHelper;
    private final UserProfileClient userProfileClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
       String targetUrl = determineTargetUrl(request, response, authentication);
       // check if the response has already been sent before
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        log.info("redirectUri: {}", redirectUri);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            try {
                throw new BadRequestException(ResultCode.SERVER_BUSY);
//            } catch (BadRequestException e) {
//                throw new RuntimeException(e);
//            }
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());


        // temp handle, will fix later to scale
        // handle open id connection
        Object principal = authentication.getPrincipal();
        if(principal instanceof DefaultOidcUser oAuth2User) {
            if(oAuth2User.getAttributes().get("email") != null) {
            String email = oAuth2User.getEmail();
                userRepository.findByEmail(email)
                        .ifPresentOrElse(
                                user -> {


                                    userProfileClient.updateProfile(UpdateProfileRequest.builder()
                                            .name(oAuth2User.getName())
                                            .username(userHelper.generateUsername(oAuth2User.getName()))
                                            .imgUrl(oAuth2User.getPicture())
                                            .userId(user.getId().toString())
                                            .authProvider(AuthProvider.AZURE.name())
                                            .build());


                                    userRepository.save(user);

                                },() -> {
                                    // create new user
                                    User newUser = new User();
                                    newUser.setEmail(email);



                                    newUser.setEnabled(true);
                                    newUser.setAuthProvider(AuthProvider.AZURE);
                                    newUser.setProviderId(oAuth2User.getIdToken().toString());
                                    newUser.setRole(roleRepository.findByName(RoleName.CUSTOMER).get());
                                    newUser.setUsername(userHelper.generateUsername(oAuth2User.getName()));
                                    newUser.setEmailVerified(true); // Any defaults or additional settings can be applied
                                    User savedUser = userRepository.save(newUser);

                                    userProfileClient.createProfile(CreateProfileRequest.builder()
                                            .email(oAuth2User.getEmail())
                                            .name(oAuth2User.getName())
                                            .username(userHelper.generateUsername(oAuth2User.getName()))
                                            .userId(savedUser.getId().toString())
                                            .imgUrl(oAuth2User.getPicture())
                                            .authProvider(AuthProvider.AZURE.name())
                                            .build());


                                }
                        );

                UserPrincipal userPrincipal = UserPrincipal.builder()
                        .email(email)
                        .build();
                String token = jwtService.generateToken(userPrincipal);
                return UriComponentsBuilder.fromUriString(targetUrl)
                        .queryParam("accessToken", token)
                        .build().toUriString();
            }
        }


        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());


// flow -> send back to client only accessToken -> client will use it to get authResponse which is included (access,refresh & userData)
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", token)
                .build().toUriString();
    }


    // make sure all info is cleared after the authentication is done
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    // this func makes sure that the redirect uri is one of the authorized redirect uris that we have in our app properties
    // -> for authorization purposes
    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // just to make sure that the host and port are the same, we don't care about the path-> flexibility for the client
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }


}