package net.nemisolv.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import net.nemisolv.inventoryservice.entity.Inventory;
import net.nemisolv.inventoryservice.payload.CheckProductAvailabilityRequest;
import net.nemisolv.inventoryservice.payload.CreateProductStockRequest;
import net.nemisolv.inventoryservice.repository.InventoryRepository;
import net.nemisolv.inventoryservice.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public Boolean checkProductAvailability(CheckProductAvailabilityRequest request) {
        return inventoryRepository.getStock(request.productId()) >= request.quantity();
    }

    @Override
    public void createStockProduct(CreateProductStockRequest request) {
        Inventory inventory = Inventory.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .build();

        inventoryRepository.save(inventory);
    }



    @Override
    public Map<Long, Integer> checkProductAvailabilityBatch(List<Long> productIds) {
        Map<Long,Integer> stockMap = new HashMap<>();
        for (Long productId : productIds) {
            stockMap.put(productId, inventoryRepository.getStock(productId));
        }
        return stockMap;
    }

    @Override
    public void updateQuantityStock(Map<Long, Integer> updateMap) {

        updateMap.forEach( (productId, quantityDelta) -> {
            Inventory inventory = inventoryRepository.findByProductId(productId);
            inventory.setQuantity(inventory.getQuantity() + quantityDelta);
            inventoryRepository.save(inventory);
        });

    }
}
