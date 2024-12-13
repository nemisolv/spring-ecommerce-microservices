package net.nemisolv.identity.payload.user;

public record FullUserInfo(
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
