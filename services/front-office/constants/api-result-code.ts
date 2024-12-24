export enum ResultCode {
    // Success codes
    SUCCESS = 200,

    // Parameter and validation errors
    PARAMS_ERROR = 4001,
    METHOD_ARGUMENT_NOT_VALID = 4002,
    ILLEGAL_ARGUMENT = 4003,
    RESOURCE_NOT_FOUND = 4004,
    UNKNOWN_ERROR = 4005,

    // General errors
    SERVER_BUSY = 400,

    // User-related errors
    USER_SESSION_EXPIRED = 20004,
    INSUFFICIENT_PERMISSIONS = 20005,
    INCORRECT_PASSWORD = 20010,
    AUTHENTICATION_FAILED = 20009,
    EMAIL_NOT_VERIFIED = 20011,
    EMPLOYEE_NOT_FOUND = 20027,
    EMPLOYEE_DISABLED = 20031,
    USER_NOT_FOUND_OR_DISABLED = 20002,
    USER_ALREADY_EXISTS = 20003,
    TOKEN_RESET_PASSWORD_USED = 20018,

    // Authorization errors
    INVALID_TOKEN = 20007,
    ROLE_NOT_FOUND = 20008,
    TOKEN_EXPIRED = 20006,
    PERMISSION_ALREADY_ASSIGNED = 20016,
    EMAIL_ALREADY_VERIFIED = 20017,

    // Customer-related errors
    CUSTOMER_NOT_FOUND = 20012,
    CUSTOMER_SAVE_FAILED = 20013,
    CUSTOMER_UPDATE_FAILED = 20014,
    CUSTOMER_DELETE_FAILED = 20015,

    // Order-related errors
    ORDER_NOT_FOUND = 30001,
    ORDER_STATUS_ERROR = 30002,
    ORDER_CANCEL_FAILED = 30003,
    ORDER_CONFIRM_FAILED = 30004,
    ORDER_PAYMENT_FAILED = 30005,
    ORDER_SHIP_FAILED = 30006,
    ORDER_RECEIVE_FAILED = 30007,
    ORDER_RETURN_FAILED = 30008,
    ORDER_REFUND_FAILED = 30009,
    ORDER_LINE_NOT_FOUND = 30010,
    ORDER_LINE_SAVE_FAILED = 30011,

    // Product-related errors
    PRODUCT_ERROR = 11001,
    PRODUCT_OUT_OF_STOCK = 11002,
    PRODUCT_APPROVAL_FAILED = 11005,
    PRODUCT_SKU_EMPTY = 11007,
    PRODUCT_NOT_EXIST = 11026,
    PRODUCT_SPECIFICATION_ERROR = 11013,
    PRODUCT_PERMISSION_ERROR = 11017,
    PRODUCT_TYPE_REQUIRED = 11016,
    PRODUCT_PURCHASE_FAILED = 11019,

    // Brand-related errors
    BRAND_SAVE_FAILED = 14001,
    BRAND_UPDATE_FAILED = 14002,
    BRAND_DISABLE_FAILED = 14003,
    BRAND_DELETE_FAILED = 14004,
    BRAND_NAME_EXISTS = 14005,
    BRAND_LINKED_TO_CATEGORY = 14006,
    BRAND_NOT_FOUND = 14007,

    // Category-related errors
    CATEGORY_NOT_FOUND = 10001,
    CATEGORY_NAME_EXISTS = 10002,
    PARENT_CATEGORY_NOT_FOUND = 10003,
    MAX_CATEGORY_LEVEL_EXCEEDED = 10004,
    CATEGORY_HAS_SUBCATEGORIES = 10005,
    CATEGORY_HAS_PRODUCTS = 10006,
    CATEGORY_CONTAINS_PRODUCTS = 10007,
    CATEGORY_PARAMETER_NOT_FOUND = 10012,
    CATEGORY_PARAMETER_ADD_FAILED = 10008,
    CATEGORY_PARAMETER_UPDATE_FAILED = 10009,
    CATEGORY_STATUS_MISMATCH = 10010,
    CATEGORY_COMMISSION_RATE_INCORRECT = 10011,

    // Parameter-related errors
    PARAMETER_SAVE_FAILED = 12001,
    PARAMETER_UPDATE_FAILED = 12002,

    // System exceptions
    RATE_LIMIT_EXCEEDED = 1003,
    SERVER_INTERNAL_ERROR = 1004,
}

export const ResultCodeMessages: Record<ResultCode, string> = {
    // Success codes
    [ResultCode.SUCCESS]: "Success",

    // Parameter and validation errors
    [ResultCode.PARAMS_ERROR]: "Parameter exception",
    [ResultCode.METHOD_ARGUMENT_NOT_VALID]: "Method argument not valid",
    [ResultCode.ILLEGAL_ARGUMENT]: "Illegal argument",
    [ResultCode.RESOURCE_NOT_FOUND]: "Resource not found",
    [ResultCode.UNKNOWN_ERROR]: "Something went wrong",

    // General errors
    [ResultCode.SERVER_BUSY]: "Server is busy, please try again later",


    // User-related errors
    [ResultCode.USER_SESSION_EXPIRED]: "User session has expired, please log in again",
    [ResultCode.INSUFFICIENT_PERMISSIONS]: "Insufficient permissions",
    [ResultCode.INCORRECT_PASSWORD]: "Incorrect password",
    [ResultCode.AUTHENTICATION_FAILED]: "Authentication failed",
    [ResultCode.EMAIL_NOT_VERIFIED]: "Email is not verified",
    [ResultCode.EMPLOYEE_NOT_FOUND]: "Employee does not exist",
    [ResultCode.EMPLOYEE_DISABLED]: "Employee has been disabled",
    [ResultCode.USER_NOT_FOUND_OR_DISABLED]: "User does not exist or account is disabled",
    [ResultCode.USER_ALREADY_EXISTS]: "User already exists",
    [ResultCode.TOKEN_RESET_PASSWORD_USED]: "Token for resetting password has been used",

    // Authorization errors
    [ResultCode.INVALID_TOKEN]: "Invalid token",
    [ResultCode.ROLE_NOT_FOUND]: "Role not found",
    [ResultCode.TOKEN_EXPIRED]: "Token has expired",
    [ResultCode.PERMISSION_ALREADY_ASSIGNED]: "Permission already assigned to role",
    [ResultCode.EMAIL_ALREADY_VERIFIED]: "User already verified",

    // Customer-related errors
    [ResultCode.CUSTOMER_NOT_FOUND]: "Customer does not exist",
    [ResultCode.CUSTOMER_SAVE_FAILED]: "Customer save failed",
    [ResultCode.CUSTOMER_UPDATE_FAILED]: "Customer update failed",
    [ResultCode.CUSTOMER_DELETE_FAILED]: "Customer delete failed",

    // Order-related errors
    [ResultCode.ORDER_NOT_FOUND]: "Order does not exist",
    [ResultCode.ORDER_STATUS_ERROR]: "Order status error",
    [ResultCode.ORDER_CANCEL_FAILED]: "Order cancellation failed",
    [ResultCode.ORDER_CONFIRM_FAILED]: "Order confirmation failed",
    [ResultCode.ORDER_PAYMENT_FAILED]: "Order payment failed",
    [ResultCode.ORDER_SHIP_FAILED]: "Order shipping failed",
    [ResultCode.ORDER_RECEIVE_FAILED]: "Order receiving failed",
    [ResultCode.ORDER_RETURN_FAILED]: "Order return failed",
    [ResultCode.ORDER_REFUND_FAILED]: "Order refund failed",
    [ResultCode.ORDER_LINE_NOT_FOUND]: "Order line does not exist",
    [ResultCode.ORDER_LINE_SAVE_FAILED]: "Order line save failed",

    // Product-related errors
    [ResultCode.PRODUCT_ERROR]: "Product error, please try again later",
    [ResultCode.PRODUCT_OUT_OF_STOCK]: "Product is out of stock",
    [ResultCode.PRODUCT_APPROVAL_FAILED]: "Product approval failed",
    [ResultCode.PRODUCT_SKU_EMPTY]: "Product SKU code cannot be empty",
    [ResultCode.PRODUCT_NOT_EXIST]: "Product does not exist",
    [ResultCode.PRODUCT_SPECIFICATION_ERROR]: "Product specification error, please refresh and try again",
    [ResultCode.PRODUCT_PERMISSION_ERROR]: "Current user does not have permission to operate this product",
    [ResultCode.PRODUCT_TYPE_REQUIRED]: "Product type must be selected",
    [ResultCode.PRODUCT_PURCHASE_FAILED]: "Product purchase failed",

    // Brand-related errors
    [ResultCode.BRAND_SAVE_FAILED]: "Add brand failed",
    [ResultCode.BRAND_UPDATE_FAILED]: "Update brand failed",
    [ResultCode.BRAND_DISABLE_FAILED]: "Disable brand failed",
    [ResultCode.BRAND_DELETE_FAILED]: "Delete brand failed",
    [ResultCode.BRAND_NAME_EXISTS]: "Brand name already exists!",
    [ResultCode.BRAND_LINKED_TO_CATEGORY]: "Category is linked to the brand, please unlink it first",
    [ResultCode.BRAND_NOT_FOUND]: "Brand does not exist",

    // Category-related errors
    [ResultCode.CATEGORY_NOT_FOUND]: "Product category does not exist",
    [ResultCode.CATEGORY_NAME_EXISTS]: "Category name already exists",
    [ResultCode.PARENT_CATEGORY_NOT_FOUND]: "Parent category does not exist",
    [ResultCode.MAX_CATEGORY_LEVEL_EXCEEDED]: "Maximum 3 levels of categories, add failed",
    [ResultCode.CATEGORY_HAS_SUBCATEGORIES]: "This category contains subcategories, cannot be deleted",
    [ResultCode.CATEGORY_HAS_PRODUCTS]: "This category contains products, cannot be deleted",
    [ResultCode.CATEGORY_CONTAINS_PRODUCTS]: "Category cannot be saved, it contains products",
    [ResultCode.CATEGORY_PARAMETER_NOT_FOUND]: "Linked parameter group for the category does not exist",
    [ResultCode.CATEGORY_PARAMETER_ADD_FAILED]: "Failed to add linked parameter group for the category",
    [ResultCode.CATEGORY_PARAMETER_UPDATE_FAILED]: "Failed to update linked parameter group for the category",
    [ResultCode.CATEGORY_STATUS_MISMATCH]: "Subcategory status cannot differ from parent category status!",
    [ResultCode.CATEGORY_COMMISSION_RATE_INCORRECT]: "Category commission rate is incorrect!",

    // Parameter-related errors
    [ResultCode.PARAMETER_SAVE_FAILED]: "Add parameter failed",
    [ResultCode.PARAMETER_UPDATE_FAILED]: "Update parameter failed",

    // System exceptions
    [ResultCode.RATE_LIMIT_EXCEEDED]: "Access too frequent, please try again later",
    [ResultCode.SERVER_INTERNAL_ERROR]: "Server is busy, please try again later",
};
