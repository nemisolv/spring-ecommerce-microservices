package net.nemisolv.lib.payload.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "Email can not be empty")
    private String email;
    @Length(min = 4, message = "Password must be at least 6 characters")
    private String password;
}
