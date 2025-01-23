package net.nemisolv.inventoryservice.payload;


public record CreateProductStockRequest(
        Long productId,
        int quantity
) {
}
