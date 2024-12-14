package net.nemisolv.notificationservice.payload;

public record OtpTokenOptional(
        String otp,
        String token
) {
}
