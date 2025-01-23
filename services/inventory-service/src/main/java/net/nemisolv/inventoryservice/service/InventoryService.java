package net.nemisolv.inventoryservice.service;

import net.nemisolv.inventoryservice.payload.CheckProductAvailabilityRequest;
import net.nemisolv.inventoryservice.payload.CreateProductStockRequest;

import java.util.List;
import java.util.Map;

public interface InventoryService {
    Boolean checkProductAvailability(CheckProductAvailabilityRequest request);

    void createStockProduct(CreateProductStockRequest request);


    // key: product id, value: quantity
    Map<Long, Integer> checkProductAvailabilityBatch(List<Long> productIds);

    void updateQuantityStock(Map<Long, Integer> updateMap);
}
