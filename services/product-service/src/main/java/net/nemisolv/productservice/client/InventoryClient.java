package net.nemisolv.productservice.client;

import jakarta.validation.Valid;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.productservice.config.FeignClientConfig;
import net.nemisolv.productservice.payload.exchange.CheckProductAvailabilityRequest;
import net.nemisolv.productservice.payload.product.request.CreateProductStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(
        name = "inventory-service",
        url = "${app.config.inventory-url}",
        configuration = FeignClientConfig.class
)
public interface InventoryClient {

    @PostMapping("/i/check-availability")
    ApiResponse<Boolean> checkProductAvailability(@RequestBody @Valid CheckProductAvailabilityRequest request);

    @PostMapping("/i/check-availability-batch")
//    @RequestMapping(method = RequestMethod.PUT, value = "/i/check-availability-batch")
    ApiResponse<Map<Long, Integer>> checkStock(@RequestBody List<Long> productIds);

    @RequestMapping(method = RequestMethod.PUT, value = "/i/update-only-quantity")
        // key: product id, value: quantity delta
    void updateQuantityInventory(@RequestBody Map<Long, Integer> updateMap);

    @PostMapping("/i/create")
    void createStockProduct(CreateProductStockRequest createProductStockRequest);
}