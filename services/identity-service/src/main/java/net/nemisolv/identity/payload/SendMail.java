package net.nemisolv.identity.payload;

import net.nemisolv.lib.payload.MailInfo;

public record SendMail(
        RecipientInfo recipient,
        MailInfo mailInfo
) {
}
