package net.nemisolv.identity.payload.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResendEmailConfirmationRequest(
        @NotNull(message = "Email is required")
        @NotBlank(message = "Email is required")
        String email) {
}
