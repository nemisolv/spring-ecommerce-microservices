package net.nemisolv.productservice.client;

import jakarta.validation.Valid;
import net.nemisolv.productservice.payload.exchange.CheckProductAvailabilityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@FeignClient(
        name = "inventory-service",
        url = "${application.config.inventory-url}"
)public interface InventoryClient {

    @PostMapping("/inventory/check-availability")
    Boolean checkProductAvailability(@RequestBody @Valid CheckProductAvailabilityRequest request);

    @PostMapping("/inventory/check-stock")
    Map<Long, Integer> checkStock(@RequestBody List<Long> productIds);

    @PostMapping("/inventory/update-stock")
    // key: product id, value: quantity delta
    void updateStock(@RequestBody Map<Long, Integer> updateMap);
}