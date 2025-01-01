package net.nemisolv.identity.payload.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import net.nemisolv.identity.payload.user.FullUserInfoResponse;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiry;
    private long refreshTokenExpiry;
    private FullUserInfoResponse userData;
}
