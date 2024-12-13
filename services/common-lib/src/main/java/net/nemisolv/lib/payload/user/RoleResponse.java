package net.nemisolv.lib.payload.user;

import net.nemisolv.techshop.core._enum.RoleName;

import java.util.Set;

public record RoleResponse(
        Long id,
        RoleName name,
        Set<String> permissions
) {
}
