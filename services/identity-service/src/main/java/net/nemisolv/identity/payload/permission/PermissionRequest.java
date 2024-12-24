package net.nemisolv.identity.payload.permission;


import net.nemisolv.lib.core._enum.PermissionName;

public record PermissionRequest(
        PermissionName name,
        String description
) {
}
