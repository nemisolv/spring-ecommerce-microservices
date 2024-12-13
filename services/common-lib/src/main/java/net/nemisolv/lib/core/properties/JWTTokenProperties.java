package net.nemisolv.lib.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "app.secure.jwt")
public class JWTTokenProperties {

    /**
     * Thời gian hết hạn mặc định của token
     */
    private long tokenExpireTime = 60;

    private long refreshTokenExpireTime = 100;

    private String secretKey;

}