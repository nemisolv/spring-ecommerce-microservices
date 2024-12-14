package net.nemisolv.orderservice.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.nemisolv.lib.payload.ApiResponse;
import net.nemisolv.orderservice.payload.OrderRequest;
import net.nemisolv.orderservice.payload.OrderResponse;
import net.nemisolv.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ApiResponse
          <Integer> createOrder(
      @RequestBody @Valid OrderRequest request
  ) {
    return ApiResponse.success(this.orderService.createOrder(request));
  }

  @GetMapping
  public ApiResponse<List<OrderResponse>> findAll() {
    return ApiResponse.success(this.orderService.findAllOrders());
  }

  @GetMapping("/{order-id}")
  public ApiResponse
          <OrderResponse> findById(
      @PathVariable("order-id") Integer orderId
  ) {
    return ApiResponse.success
            (this.orderService.findById(orderId));
  }
}