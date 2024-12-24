package net.nemisolv.orderservice.service;

import lombok.RequiredArgsConstructor;
import net.nemisolv.orderservice.mapper.OrderLineMapper;
import net.nemisolv.orderservice.payload.OrderLineRequest;
import net.nemisolv.orderservice.payload.OrderLineResponse;
import net.nemisolv.orderservice.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}