package net.nemisolv.notificationservice.payload;

import lombok.Builder;

@Builder
public record RecipientInfo(
        String email,
        String name

) {




}
