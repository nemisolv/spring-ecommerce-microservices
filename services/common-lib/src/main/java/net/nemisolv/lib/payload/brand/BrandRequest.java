package net.nemisolv.lib.payload.brand;

import jakarta.validation.constraints.NotEmpty;

public record BrandRequest(
        @NotEmpty(message = "Brand name is required")
        String name,
        String description,
        @NotEmpty(message = "Brand logo is required")
        String logoUrl
) {
}
