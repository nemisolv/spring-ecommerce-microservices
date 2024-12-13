package net.nemisolv.identity.properties;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app")
@Component
@Getter
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    public static class Auth {
        @Value("${secure.jwt.secret-key}")
        private String tokenSecret;
        @Value("${secure.jwt.token-expire")
        private long tokenExpirationMsec;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OAuth2 {
        @Value("${oauth2.authorizedRedirectUris}")
        private List<String> authorizedRedirectUris = new ArrayList<>();
    }


}
