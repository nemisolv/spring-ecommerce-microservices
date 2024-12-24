package net.nemisolv.notificationservice.kafka.order;

public record Customer(
    String id,
    String name,
    String email
) {

}