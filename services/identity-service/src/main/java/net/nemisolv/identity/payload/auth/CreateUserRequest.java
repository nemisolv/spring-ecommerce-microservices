package net.nemisolv.identity.payload.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequest(
        @NotBlank(message = "Email can not be empty")
        String email,
        @Length(min = 6, message = "Password must be at least 6 characters")
        String password,
        @NotEmpty(message = "Name can not be empty")
        @Length(min = 2, message = "Name must be at least 2 characters")
        String name
) {


}
