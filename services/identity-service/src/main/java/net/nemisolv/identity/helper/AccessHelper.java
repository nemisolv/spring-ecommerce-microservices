package net.nemisolv.identity.helper;

import net.nemisolv.identity.entity.Permission;
import net.nemisolv.identity.entity.Role;
import net.nemisolv.identity.security.UserPrincipal;
import net.nemisolv.identity.security.context.AuthContext;
import net.nemisolv.lib.core._enum.PermissionName;
import net.nemisolv.lib.core._enum.RoleName;

import java.util.List;
import java.util.Set;

public class AccessHelper {

    private static final  Set<PermissionName> basicPermissions = Set.of(
            PermissionName.CREATE_USER,
            PermissionName.UPDATE_USER,
            PermissionName.VIEW_USER,
            PermissionName.CREATE_PRODUCT,
            PermissionName.VIEW_PRODUCT,

            // Inventory Management
            PermissionName.VIEW_INVENTORY,

            // Order Management
            PermissionName.UPDATE_ORDER,
            PermissionName.VIEW_ORDER,

            // Report Management
            PermissionName.VIEW_SALES_REPORT,
            PermissionName.VIEW_INVENTORY_REPORT,

            // Inventory Management
            PermissionName.UPDATE_STOCK,

            // Brand Management
            PermissionName.VIEW_BRAND
    );


    public static boolean isAccessAllowed(PermissionName action) {
        UserPrincipal currentUser = AuthContext.getCurrentUser();

        if (currentUser == null || currentUser.getAuthorities() == null) {
            return false; // Handle case where user or authorities are not defined
        }

        Role role = currentUser.getRole();
        if (role == null || role.getName() == null || role.getPermissions() == null) {
            return false; // Handle case where role is not defined
        }

        if (role.getName().equals(RoleName.ADMIN)) {
            return true; // Admin has access to all actions
        }


        return role.getPermissions().stream().anyMatch(permission -> permission.getName().equals(action));
    }


    public static boolean isBasicPermission(Permission permission) {

        return basicPermissions.contains(PermissionName.valueOf(permission.getName().name()));
    }

    public static  List<Permission> filterBasicPermissions(List<Permission> permissions) {
        return permissions.stream()
                .filter(permission -> basicPermissions.contains(permission.getName()))
                .toList();
    }
}
