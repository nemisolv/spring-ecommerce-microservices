package net.nemisolv.lib.payload.permission;

import jakarta.validation.constraints.NotNull;

public record AssignPermissionToRoleRequest(
        @NotNull(message = "roleId is required") Long roleId,
        @NotNull(message = "permissionId is required")
        Long permissionId
) {
}
