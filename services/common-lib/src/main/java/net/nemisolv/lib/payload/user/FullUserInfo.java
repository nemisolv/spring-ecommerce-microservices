package net.nemisolv.lib.payload.user;

import java.util.Set;

public record FullUserInfo(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        boolean emailVerified,
        String imgUrl,
        RoleResponse role,
        String authProvider
) {
}
