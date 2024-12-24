package net.nemisolv.identity.payload.auth;

public record VerifyEmailWithOtpRequest(
        String otp
) {
}
