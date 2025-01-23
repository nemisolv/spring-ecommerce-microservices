package net.nemisolv.productservice.payload.product.request;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProductRequest(
        String name,
        String description,
        String mainImgUrl,
        BigDecimal price,
        String unit,
        Integer quantity,
        String videoUrl,
        String type,
        boolean active,
        String reviewStory,
        Long brandId,
        Long categoryId,
        List<CreateProductVariantRequest> variants
) {
}
