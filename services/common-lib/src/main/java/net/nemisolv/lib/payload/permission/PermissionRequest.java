package net.nemisolv.lib.payload.permission;

import net.nemisolv.techshop.core._enum.PermissionName;

public record PermissionRequest(
        PermissionName name,
        String description
) {
}
