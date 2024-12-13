package net.nemisolv.lib.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationConfigChecker implements CommandLineRunner {

    // Datasource configuration properties
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    // Mail configuration properties
    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    // OAuth2 configurations for social logins
    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
    private String facebookClientId;

    @Value("${spring.security.oauth2.client.registration.facebook.clientSecret}")
    private String facebookClientSecret;

    @Value("${spring.security.oauth2.client.registration.github.clientId}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.registration.github.clientSecret}")
    private String githubClientSecret;

    // JWT configurations
    @Value("${app.secure.jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${app.secure.jwt.tokenExpireTime}")
    private Long tokenExpireTime;

    @Value("${app.secure.jwt.refreshTokenExpireTime}")
    private Long refreshTokenExpireTime;

    // OAuth2 redirect URIs to check
//    @Value("${app.oauth2.authorizedRedirectUris}")
//    private String[] authorizedRedirectUris;

    @Override
    public void run(String... args) {
        checkDatasourceConfiguration();
        checkMailConfiguration();
        checkOAuth2Configurations();
        checkJWTConfiguration();
        checkRequiredUrls();
    }

    private void checkDatasourceConfiguration() {
        if (datasourceUrl == null || datasourceUrl.isEmpty()) {
            throw new RuntimeException("Datasource URL is not set correctly!");
        } else {
            log.info("Datasource URL: {}", datasourceUrl);
        }

        if (datasourceUsername == null || datasourceUsername.isEmpty()) {
            throw new RuntimeException("Datasource Username is not set correctly!");
        } else {
            log.info("Datasource Username: {}", datasourceUsername);
        }

        if (datasourcePassword == null || datasourcePassword.isEmpty()) {
            throw new RuntimeException("Datasource Password is not set correctly!");
        } else {
            log.info("Datasource Password is configured.");
        }
    }

    private void checkMailConfiguration() {
        if (mailHost == null || mailHost.isEmpty()) {
            throw new RuntimeException("Mail host is not set correctly!");
        } else {
            log.info("Mail host: {}", mailHost);
        }

        if (mailUsername == null || mailUsername.isEmpty()) {
            throw new RuntimeException("Mail username is not set correctly!");
        } else {
            log.info("Mail username: {}", mailUsername);
        }

        if (mailPassword == null || mailPassword.isEmpty()) {
            throw new RuntimeException("Mail password is not set correctly!");
        } else {
            log.info("Mail password is configured.");
        }
    }

    private void checkOAuth2Configurations() {
        if (googleClientId == null || googleClientId.isEmpty()) {
            throw new RuntimeException("Google OAuth2 Client ID is not set correctly!");
        } else {
            log.info("Google OAuth2 Client ID: {}", googleClientId);
        }

        if (googleClientSecret == null || googleClientSecret.isEmpty()) {
            throw new RuntimeException("Google OAuth2 Client Secret is not set correctly!");
        } else {
            log.info("Google OAuth2 Client Secret is configured.");
        }

        if (facebookClientId == null || facebookClientId.isEmpty()) {
            throw new RuntimeException("Facebook OAuth2 Client ID is not set correctly!");
        } else {
            log.info("Facebook OAuth2 Client ID: {}", facebookClientId);
        }

        if (facebookClientSecret == null || facebookClientSecret.isEmpty()) {
            throw new RuntimeException("Facebook OAuth2 Client Secret is not set correctly!");
        } else {
            log.info("Facebook OAuth2 Client Secret is configured.");
        }

        if (githubClientId == null || githubClientId.isEmpty()) {
            throw new RuntimeException("GitHub OAuth2 Client ID is not set correctly!");
        } else {
            log.info("GitHub OAuth2 Client ID: {}", githubClientId);
        }

        if (githubClientSecret == null || githubClientSecret.isEmpty()) {
            throw new RuntimeException("GitHub OAuth2 Client Secret is not set correctly!");
        } else {
            log.info("GitHub OAuth2 Client Secret is configured.");
        }
    }

    private void checkJWTConfiguration() {
        if (jwtSecretKey == null || jwtSecretKey.isEmpty()) {
            throw new RuntimeException("JWT Secret Key is not set correctly!");
        } else {
            log.info("JWT Secret Key is configured.");
        }

        if (tokenExpireTime == null || tokenExpireTime <= 0) {
            throw new RuntimeException("JWT Token Expiration Time is not set correctly!");
        } else {
            log.info("JWT Token Expiration Time: {} ms", tokenExpireTime);
        }

        if (refreshTokenExpireTime == null || refreshTokenExpireTime <= 0) {
            throw new RuntimeException("JWT Refresh Token Expiration Time is not set correctly!");
        } else {
            log.info("JWT Refresh Token Expiration Time: {} ms", refreshTokenExpireTime);
        }
    }

    private void checkRequiredUrls() {
//        if (authorizedRedirectUris == null || authorizedRedirectUris.length == 0) {
//            throw new RuntimeException("OAuth2 Redirect URIs are not set correctly!");
//        }
//
//        for (String uri : authorizedRedirectUris) {
//            if (uri == null || uri.isEmpty()) {
//                throw new RuntimeException("OAuth2 Redirect URI is empty or missing!");
//            }
//        }
//
//        log.info("OAuth2 Authorized Redirect URIs: {}", String.join(", ", authorizedRedirectUris));
    }
}
