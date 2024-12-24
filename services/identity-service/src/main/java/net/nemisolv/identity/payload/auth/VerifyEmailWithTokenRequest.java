package net.nemisolv.identity.payload.auth;

public record VerifyEmailWithTokenRequest(
        String token
) {
}
