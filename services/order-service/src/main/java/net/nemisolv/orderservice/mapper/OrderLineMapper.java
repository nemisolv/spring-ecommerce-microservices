package net.nemisolv.orderservice.mapper;

import net.nemisolv.orderservice.entity.Order;
import net.nemisolv.orderservice.entity.OrderLine;
import net.nemisolv.orderservice.payload.OrderLineRequest;
import net.nemisolv.orderservice.payload.OrderLineResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.orderId())
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}