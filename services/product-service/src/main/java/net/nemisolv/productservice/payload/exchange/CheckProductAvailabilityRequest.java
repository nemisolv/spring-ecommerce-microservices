package net.nemisolv.productservice.payload.exchange;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CheckProductAvailabilityRequest(
        @NotNull(message = "Product ID is required")
        Long productId,

        @Min(value = 1, message = "Quantity must be greater than 0")
        @NotNull(message = "Quantity is required")
        Integer quantity
) {
}
