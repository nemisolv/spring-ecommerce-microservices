package net.nemisolv.identity.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import net.nemisolv.identity.security.JwtAuthenticationFilter;
import net.nemisolv.identity.security.oauth2.CustomOAuth2UserService;
import net.nemisolv.identity.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import net.nemisolv.identity.security.oauth2.OAuth2AuthenticationFailureHandler;
import net.nemisolv.identity.security.oauth2.OAuth2AuthenticationSuccessHandler;
import net.nemisolv.identity.properties.IgnoredUrlsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;
    private final IgnoredUrlsProperties ignoredUrlsProperties;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(req -> req.requestMatchers("/v1/api/auth/**").permitAll()
                        .requestMatchers(ignoredUrlsProperties.getUrls().toArray(new String[0])).permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/comments/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/global/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(authException.getMessage());
                })) .oauth2Login(oauth2 -> oauth2.authorizationEndpoint(authorization -> authorization.baseUri("/oauth2/authorize")
                                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository))
                        .redirectionEndpoint(redirection -> redirection.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .failureHandler(oAuth2AuthenticationFailureHandler)
                        .successHandler(oAuth2AuthenticationSuccessHandler)

                );

        return http.build();
    }


}