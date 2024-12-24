package net.nemisolv.orderservice.mapper;

import net.nemisolv.orderservice.entity.Order;
import net.nemisolv.orderservice.payload.OrderRequest;
import net.nemisolv.orderservice.payload.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  Order toOrder(OrderRequest request) ;

  OrderResponse fromOrder(Order order) ;
}