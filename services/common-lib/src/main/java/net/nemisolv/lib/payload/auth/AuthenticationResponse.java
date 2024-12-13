package net.nemisolv.lib.payload.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import net.nemisolv.techshop.payload.user.FullUserInfo;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private FullUserInfo userData;
}
