package net.nemisolv.productservice.payload.product.request;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Description is mandatory")
        String description,

        @NotBlank(message = "Main image URL is mandatory")
        String mainImgUrl,

        @NotNull(message = "Price is mandatory")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        BigDecimal price,

        @NotBlank(message = "Unit is mandatory")
        String unit,

        @NotNull(message = "Quantity is mandatory")
        @Min(value = 0, message = "Quantity must be zero or greater")
        Integer quantity,

        String videoUrl,


        boolean active,

        String reviewStory,

        @NotNull(message = "Category ID is mandatory")
        Long categoryId,


        JsonNode variants
) {
}