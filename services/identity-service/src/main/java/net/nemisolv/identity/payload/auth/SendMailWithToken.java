package net.nemisolv.identity.payload.auth;

import net.nemisolv.identity.payload.RecipientInfo;

public record SendMailWithToken(RecipientInfo recipient, String token) {
}
