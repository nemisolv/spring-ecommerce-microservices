package net.nemisolv.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.nemisolv.inventoryservice.payload.CheckProductAvailabilityRequest;
import net.nemisolv.inventoryservice.payload.CreateProductStockRequest;
import net.nemisolv.inventoryservice.service.InventoryService;
import net.nemisolv.lib.payload.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/i")
@RequiredArgsConstructor

public class InventoryController {
    private final InventoryService inventoryService;


    @PostMapping("/check-availability")
    public ApiResponse<Boolean> checkProductAvailability(@RequestBody @Valid CheckProductAvailabilityRequest request) {
        return ApiResponse.success(inventoryService.checkProductAvailability(request));
    }

    @PostMapping("/check-availability-batch")
    public ApiResponse<Map<Long, Integer>> checkStock(@RequestBody List<Long> productIds) {
        return ApiResponse.success(inventoryService.checkProductAvailabilityBatch(productIds));
    }


    @PostMapping("/create")
    public ApiResponse<Void> createNewStock(@RequestBody CreateProductStockRequest request) {
        inventoryService.createStockProduct(request);
        return ApiResponse.success("Stock created successfully");
    }


    @PutMapping("/update-only-quantity")
    public ApiResponse<Void> updateQuantityStock(@RequestBody Map<Long, Integer> updateMap) {
        inventoryService.updateQuantityStock(updateMap);
        return ApiResponse.success("Stock quantity updated successfully");
    }
}
