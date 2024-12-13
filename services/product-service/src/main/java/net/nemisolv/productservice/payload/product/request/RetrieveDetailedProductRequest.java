package net.nemisolv.productservice.payload.product.request;

public record RetrieveDetailedProductRequest(
        Long id,
        boolean active
) {
}
