package net.nemisolv.identity.payload.auth;

public record OtpTokenOptional(
        String otp,
        String token
) {
}
