package net.nemisolv.identity.security.oauth2;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.nemisolv.identity.entity.User;
import net.nemisolv.identity.helper.UserHelper;
import net.nemisolv.identity.repository.RoleRepository;
import net.nemisolv.identity.repository.UserRepository;
import net.nemisolv.identity.security.UserPrincipal;
import net.nemisolv.identity.security.oauth2.user.OAuth2UserInfo;
import net.nemisolv.identity.security.oauth2.user.OAuth2UserInfoFactory;
import net.nemisolv.lib.core._enum.AuthProvider;
import net.nemisolv.lib.core._enum.RoleName;
import net.nemisolv.lib.core.exception.OAuth2AuthenticationProcessionException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepo;
    private final UserHelper userHelper;
private final RoleRepository roleRepo;
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return processOAuth2User(userRequest, oAuth2User);

        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // trigger OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }

    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws OAuth2AuthenticationProcessionException {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOauth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes());
    //because gitHub does not provide email, we don't need to check for email
        if (!StringUtils.hasLength(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessionException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepo.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getAuthProvider().equals(AuthProvider.getEnum(userRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessionException("Looks like you're signed up with " +
                        user.getAuthProvider().getValue() + " account. Please use your " + user.getAuthProvider().getValue() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo, userRequest);
        } else {
            user = registerNewUser(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user);
    }

    private User registerNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oauth2UserInfo) {

        User user = new User();

        user.setName(oauth2UserInfo.getName());
        user.setEmail(oauth2UserInfo.getEmail());
        String username = userHelper.generateUsername(oauth2UserInfo.getName());
        user.setUsername(username);
        user.setEmailVerified(true);
        user.setAuthProvider(AuthProvider.getEnum(userRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oauth2UserInfo.getId());
        user.setImgUrl(oauth2UserInfo.getImageUrl());
        user.setEnabled(true);
        user.setRole(roleRepo.findByName(RoleName.CUSTOMER).orElseThrow(() -> new RuntimeException("User Role not set.")));
        return userRepo.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oauth2UserInfo, OAuth2UserRequest userRequest) {
        // allow user to update photo
//        existingUser.setPicture(oauth2UserInfo.getImageUrl());
        existingUser.setProviderId(oauth2UserInfo.getId());
        existingUser.setAuthProvider(AuthProvider.getEnum(userRequest.getClientRegistration().getRegistrationId()));
        return userRepo.save(existingUser);
    }


}