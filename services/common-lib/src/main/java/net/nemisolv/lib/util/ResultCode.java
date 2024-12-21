package net.nemisolv.lib.util;

public enum ResultCode {

    // Success codes
    SUCCESS(200, "Success"),

    // Parameter and validation errors
    PARAMS_ERROR(4001, "Parameter exception"),
    METHOD_ARGUMENT_NOT_VALID(4002, "Method argument not valid"),
    ILLEGAL_ARGUMENT(4003, "Illegal argument"),
    RESOURCE_NOT_FOUND(4004, "Resource not found"),

    // General errors
    SERVER_BUSY(400, "Server is busy, please try again later"),
    BAD_REQUEST(400, "Bad request"),

    // User-related errors
    USER_SESSION_EXPIRED(20004, "User session has expired, please log in again"),
    INSUFFICIENT_PERMISSIONS(20005, "Insufficient permissions"),
    INCORRECT_PASSWORD(20010, "Incorrect password"),
    AUTHENTICATION_FAILED(20009, "Authentication failed"),
    EMAIL_NOT_VERIFIED(20011, "Email is not verified"),
    EMPLOYEE_NOT_FOUND(20027, "Employee does not exist"),
    EMPLOYEE_DISABLED(20031, "Employee has been disabled"),
    USER_NOT_FOUND_OR_DISABLED(20002, "User does not exist or account is disabled"),
    USER_ALREADY_EXISTS(20003, "User already exists"),

    // Authorization errors
    INVALID_TOKEN(20007, "Invalid token"),
    ROLE_NOT_FOUND(20008, "Role not found"),
    TOKEN_EXPIRED(20006, "Token has expired"),

    PERMISSION_ALREADY_ASSIGNED(20016, "Permission already assigned to role"),
    EMAIL_ALREADY_VERIFIED(20017, "Email already verified"),
    TOKEN_RESET_PASSWORD_USED(20018, "Token for resetting password has been used"),

    // Customer-related errors
    CUSTOMER_NOT_FOUND(20012, "Customer does not exist"),
    CUSTOMER_SAVE_FAILED(20013, "Customer save failed"),
    CUSTOMER_UPDATE_FAILED(20014, "Customer update failed"),
    CUSTOMER_DELETE_FAILED(20015, "Customer delete failed"),

    // Order-related errors
    ORDER_NOT_FOUND(30001, "Order does not exist"),
    ORDER_STATUS_ERROR(30002, "Order status error"),
    ORDER_CANCEL_FAILED(30003, "Order cancellation failed"),
    ORDER_CONFIRM_FAILED(30004, "Order confirmation failed"),
    ORDER_PAYMENT_FAILED(30005, "Order payment failed"),
    ORDER_SHIP_FAILED(30006, "Order shipping failed"),
    ORDER_RECEIVE_FAILED(30007, "Order receiving failed"),
    ORDER_RETURN_FAILED(30008, "Order return failed"),
    ORDER_REFUND_FAILED(30009, "Order refund failed"),
    ORDER_LINE_NOT_FOUND(30010, "Order line does not exist"),
    ORDER_LINE_SAVE_FAILED(30011, "Order line save failed"),

    // Product-related errors
    PRODUCT_ERROR(11001, "Product error, please try again later"),
    PRODUCT_OUT_OF_STOCK(11002, "Product is out of stock"),
    PRODUCT_APPROVAL_FAILED(11005, "Product approval failed"),
    PRODUCT_SKU_EMPTY(11007, "Product SKU code cannot be empty"),
    PRODUCT_NOT_EXIST(11026, "Product does not exist"),
    PRODUCT_SPECIFICATION_ERROR(11013, "Product specification error, please refresh and try again"),
    PRODUCT_PERMISSION_ERROR(11017, "Current user does not have permission to operate this product"),
    PRODUCT_TYPE_REQUIRED(11016, "Product type must be selected"),
    PRODUCT_PURCHASE_FAILED(11019, "Product purchase failed"),

    // Brand-related errors
    BRAND_SAVE_FAILED(14001, "Add brand failed"),
    BRAND_UPDATE_FAILED(14002, "Update brand failed"),
    BRAND_DISABLE_FAILED(14003, "Disable brand failed"),
    BRAND_DELETE_FAILED(14004, "Delete brand failed"),
    BRAND_NAME_EXISTS(14005, "Brand name already exists!"),
    BRAND_LINKED_TO_CATEGORY(14006, "Category is linked to the brand, please unlink it first"),
    BRAND_NOT_FOUND(14007, "Brand does not exist"),

    // Category-related errors
    CATEGORY_NOT_FOUND(10001, "Product category does not exist"),
    CATEGORY_NAME_EXISTS(10002, "Category name already exists"),
    PARENT_CATEGORY_NOT_FOUND(10003, "Parent category does not exist"),
    MAX_CATEGORY_LEVEL_EXCEEDED(10004, "Maximum 3 levels of categories, add failed"),
    CATEGORY_HAS_SUBCATEGORIES(10005, "This category contains subcategories, cannot be deleted"),
    CATEGORY_HAS_PRODUCTS(10006, "This category contains products, cannot be deleted"),
    CATEGORY_CONTAINS_PRODUCTS(10007, "Category cannot be saved, it contains products"),
    CATEGORY_PARAMETER_NOT_FOUND(10012, "Linked parameter group for the category does not exist"),
    CATEGORY_PARAMETER_ADD_FAILED(10008, "Failed to add linked parameter group for the category"),
    CATEGORY_PARAMETER_UPDATE_FAILED(10009, "Failed to update linked parameter group for the category"),
    CATEGORY_STATUS_MISMATCH(10010, "Subcategory status cannot differ from parent category status!"),
    CATEGORY_COMMISSION_RATE_INCORRECT(10011, "Category commission rate is incorrect!"),

    // Parameter-related errors
    PARAMETER_SAVE_FAILED(12001, "Add parameter failed"),
    PARAMETER_UPDATE_FAILED(12002, "Update parameter failed"),

    // System exceptions
    RATE_LIMIT_EXCEEDED(1003, "Access too frequent, please try again later"),
    SERVER_INTERNAL_ERROR(1004, "Server is busy, please try again later");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
