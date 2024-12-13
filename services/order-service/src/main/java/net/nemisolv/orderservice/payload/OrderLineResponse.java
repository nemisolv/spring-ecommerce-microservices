package net.nemisolv.orderservice.payload;

public record OrderLineResponse(
        Integer id,
        double quantity
) { }