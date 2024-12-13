package net.nemisolv.identity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "app.secure.jwt")
public class JWTTokenProperties {

    private long tokenExpireTime = 60;

    private long refreshTokenExpireTime = 100;

    private String secretKey;

}