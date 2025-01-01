package net.nemisolv.identity.payload.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserCreationRequest(
        String username,
        @NotNull(message = "Email is required")
        @NotEmpty(message = "Email is required")
        String email,
        @NotEmpty(message = "Name is required")
        String name,
        String phoneNumber,
        String imgUrl,
        String address
) {
}
