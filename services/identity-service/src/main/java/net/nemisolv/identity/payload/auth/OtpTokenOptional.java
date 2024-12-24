package net.nemisolv.identity.payload.auth;

import lombok.Builder;

@Builder
// Using the constructor of a record can be risky due to parameter order
public record OtpTokenOptional(
        String otp,
        String token
) {
}
