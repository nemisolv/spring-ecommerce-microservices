package net.nemisolv.lib.payload;

import jakarta.validation.constraints.NotNull;
import net.nemisolv.lib.core._enum.MailType;

public record MailInfo(
        @NotNull(message = "Mail type is required")
        MailType type,
        String tokenInstruction

) {
}
