package net.nemisolv.identity.payload.user;

import lombok.Builder;

@Builder
public record FullUserInfoResponse(
        Long id,
        String username,
        String email,
        String name,
        boolean emailVerified,
        String imgUrl,
        RoleResponse role,
        String authProvider
) {
}
