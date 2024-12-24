package net.nemisolv.orderservice.service;

import net.nemisolv.orderservice.payload.OrderRequest;
import net.nemisolv.orderservice.payload.OrderResponse;

import java.util.List;

public interface OrderService {

    Integer createOrder(OrderRequest request);

    List<OrderResponse> findAllOrders();

    OrderResponse findById(Integer orderId);

}