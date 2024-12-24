package net.nemisolv.orderservice.payload;

public record CustomerResponse(
    String id,
    String name,
    String email
) {

}