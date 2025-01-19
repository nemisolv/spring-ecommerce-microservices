package net.nemisolv.lib.core._enum;

import lombok.Getter;

@Getter
public enum PermissionName {

    //Role Management
    ASSIGN_ROLE("ASSIGN_ROLE", "Can assign roles to users"),

    // User Management
    CREATE_USER("CREATE_USER", "Can create users"),
    UPDATE_USER("UPDATE_USER", "Can update users"),
    DELETE_USER("DELETE_USER", "Can delete users"),
    VIEW_USER("VIEW_USER", "Can view users"),

    // Product Management
    CREATE_PRODUCT("CREATE_PRODUCT", "Can create products"),
    UPDATE_PRODUCT("UPDATE_PRODUCT", "Can update products"),
    DELETE_PRODUCT("DELETE_PRODUCT", "Can delete products"),
    VIEW_PRODUCT("VIEW_PRODUCT", "Can view products"),
    VIEW_SENSITIVE_PRODUCT("VIEW_SENSITIVE_PRODUCT", "Can view sensitive product information"),

    // Inventory Management
    VIEW_INVENTORY("VIEW_INVENTORY", "Can view inventory"),
    UPDATE_INVENTORY("UPDATE_INVENTORY", "Can update inventory"),

    // Order Management
    CREATE_ORDER("CREATE_ORDER", "Can create orders"),
    UPDATE_ORDER("UPDATE_ORDER", "Can update orders"),
    DELETE_ORDER("DELETE_ORDER", "Can delete orders"),
    VIEW_ORDER("VIEW_ORDER", "Can view orders"),

    // Report Management
    VIEW_SALES_REPORT("VIEW_SALES_REPORT", "Can view sales reports"),
    VIEW_INVENTORY_REPORT("VIEW_INVENTORY_REPORT", "Can view inventory reports"),

    // Inventory Management
    MANAGE_INVENTORY("MANAGE_INVENTORY", "Can manage inventory"),
    UPDATE_STOCK("UPDATE_STOCK", "Can update stock levels"),

    // Brand Management
    CREATE_BRAND("CREATE_BRAND", "Can create brands"),
    UPDATE_BRAND("UPDATE_BRAND", "Can update brands"),
    DELETE_BRAND("DELETE_BRAND", "Can delete brands"),
    VIEW_BRAND("VIEW_BRAND", "Can view brands"),

    // Category Management
    CREATE_CATEGORY("CREATE_CATEGORY", "Can create categories"),
    UPDATE_CATEGORY("UPDATE_CATEGORY", "Can update categories"),
    DELETE_CATEGORY("DELETE_CATEGORY", "Can delete categories");

    private final String code;
    private final String description;

    PermissionName(String code, String description) {
        this.code = code;
        this.description = description;
    }



}
