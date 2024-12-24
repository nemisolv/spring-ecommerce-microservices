package net.nemisolv.identity.payload.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @NotEmpty(message = "Name can not be empty")
    @Length(min = 2, message = "Name must be at least 2 characters")
    private String name;

}
