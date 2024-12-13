package net.nemisolv.lib.payload.brand;

public record BrandResponse(
        Long id,
        String name,
        String description,
        String logoUrl
) {
}
