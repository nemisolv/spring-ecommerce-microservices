package net.nemisolv.lib.payload.product.request;

public record RetrieveDetailedProductRequest(
        Long id,
        boolean active
) {
}
