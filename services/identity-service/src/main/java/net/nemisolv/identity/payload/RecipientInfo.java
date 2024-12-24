package net.nemisolv.identity.payload;

import lombok.Builder;

@Builder
// Using the constructor of a record can be risky due to parameter order
public record RecipientInfo(
        String email,
        String name

) {




}
