package net.nemisolv.identity.payload.auth;

import jakarta.validation.constraints.NotEmpty;

public record IntrospectRequest(
        @NotEmpty(message = "Token is required")
        String token) {
}
