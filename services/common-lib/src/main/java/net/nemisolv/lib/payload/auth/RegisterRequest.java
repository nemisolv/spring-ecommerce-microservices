package net.nemisolv.lib.payload.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Email can not be empty")
    private String email;
    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotEmpty(message = "First name can not be empty")
    @Length(min = 2, message = "First name must be at least 2 characters")
    private String firstName;
    private String lastName;

}
