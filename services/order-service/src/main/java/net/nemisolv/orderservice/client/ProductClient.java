package net.nemisolv.orderservice.client;

import net.nemisolv.orderservice.payload.PurchaseRequest;
import net.nemisolv.orderservice.payload.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        name = "product-service",
        url = "${application.config.product-url}"
)public interface ProductClient {
    @GetMapping("/purchase")
    List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody);


}