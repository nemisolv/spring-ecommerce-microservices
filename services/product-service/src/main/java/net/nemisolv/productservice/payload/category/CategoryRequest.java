package net.nemisolv.productservice.payload.category;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record CategoryRequest(
        @NotEmpty(message = "Category name is required")
        @Length(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
        String name,
        String description,

        Long parentId
) {
}
