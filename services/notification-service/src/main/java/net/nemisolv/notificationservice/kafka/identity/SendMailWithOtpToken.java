package net.nemisolv.notificationservice.kafka.identity;


import net.nemisolv.notificationservice.payload.OtpTokenOptional;
import net.nemisolv.notificationservice.payload.RecipientInfo;

public record SendMailWithOtpToken(RecipientInfo recipient, OtpTokenOptional otpTokenOptional) {
}
