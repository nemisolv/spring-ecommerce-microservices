package net.nemisolv.lib.payload.product.request;

import com.fasterxml.jackson.databind.JsonNode;

public record UpdateProductRequest(
        String name,
        String description,
        String mainImgUrl,
        String price,
        String unit,
        Integer quantity,
        String videoUrl,
        String type,
        boolean active,
        String reviewStory,
        Long brandId,
        Long categoryId,
        JsonNode variants
) {
}
