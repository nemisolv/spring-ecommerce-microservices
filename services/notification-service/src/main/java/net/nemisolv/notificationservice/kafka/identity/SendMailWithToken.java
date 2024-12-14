package net.nemisolv.notificationservice.kafka.identity;


import net.nemisolv.notificationservice.payload.RecipientInfo;

public record SendMailWithToken(RecipientInfo recipient, String token) {
}
