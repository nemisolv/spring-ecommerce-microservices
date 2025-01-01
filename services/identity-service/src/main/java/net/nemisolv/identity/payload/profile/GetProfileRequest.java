package net.nemisolv.identity.payload.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GetProfileRequest(
        @NotNull(message = "User id is required")
        @NotEmpty(message = "User id is required")
        String userId
) {
}
