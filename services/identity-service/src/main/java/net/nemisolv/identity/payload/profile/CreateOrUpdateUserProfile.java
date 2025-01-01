package net.nemisolv.identity.payload.profile;

import lombok.Builder;

@Builder
public record CreateOrUpdateUserProfile(
        String username,
        String name,
        String phoneNumber,
        String imgUrl,
        String email,
        String address,
        String userId
) {
}
