package net.nemisolv.productservice.payload.product.response;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String mainImgUrl,
        BigDecimal price,
        String sku,
        String unit,
        Integer quantity,
        Float averageRating,
        String videoUrl,
        boolean active,
        String reviewStory,
        Long brandId,
        Long categoryId
) {
}
