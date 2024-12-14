package net.nemisolv.identity.payload.auth;

import net.nemisolv.identity.payload.RecipientInfo;

public record SendMailWithOtpToken(RecipientInfo recipient, OtpTokenOptional
                                 otpTokenOptional) {
}
