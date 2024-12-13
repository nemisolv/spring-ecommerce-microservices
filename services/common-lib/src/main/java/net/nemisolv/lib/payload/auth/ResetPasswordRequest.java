package net.nemisolv.lib.payload.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter

public class ResetPasswordRequest {
    @NotBlank(message = "Token is required")
    private String token;
    @NotBlank(message = "New password is required")
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;

}
