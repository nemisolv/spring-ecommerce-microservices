package net.nemisolv.identity.payload.user;


import net.nemisolv.lib.core._enum.RoleName;

import java.util.Set;

public record RoleResponse(
        Long id,
        RoleName name,
        Set<String> permissions
) {
}
