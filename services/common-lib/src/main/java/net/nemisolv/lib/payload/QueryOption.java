package net.nemisolv.lib.payload;

import jakarta.validation.constraints.Min;

public record QueryOption(
        @Min(value = 1, message = "Page number must be greater than 0")
        int page,
        @Min(value = 1, message = "Limit must be greater than 0")
        int limit,

        String sortBy,
        String sortDirection,
        String searchQuery,
        String filterValue,
        boolean active
) {
}
