package net.nemisolv.productservice.payload.product.request;


public record CreateProductStockRequest(
        Long productId,
        int quantity
) {
}
