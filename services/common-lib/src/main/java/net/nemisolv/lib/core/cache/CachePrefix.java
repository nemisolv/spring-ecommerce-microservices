package net.nemisolv.lib.core.cache;



public enum CachePrefix {

    /**
     * Cây phân cấp danh mục sản phẩm
     */
    PRODUCT_CATEGORY,

    /**
     * Danh sách danh mục sản phẩm
     */
    CATEGORY_ARRAY,


    /**
     * Store user menu
     */
    STORE_MENU_USER,

    /**
     * Permissions
     */
    PERMISSION_LIST,
    /**
     * Inviter
     */
    INVITER,
    /**
     * access token
     */
    ACCESS_TOKEN,
    /**
     * refresh token
     */
    REFRESH_TOKEN,

    /**
     * San pham
     */
    PRODUCT,

    /**
     * Product sku
     */
    PRODUCT_SKU;


    public static String removePrefix(String string) {
        return string.substring(string.lastIndexOf("}_") + 2);
    }

    /**
     * Lấy giá trị khóa cache chung
     *
     * @return Giá trị khóa cache
     */
    public String getPrefix() {
        return "{" + this.name() + "}_";
    }

    /**
     * Lấy giá trị khóa cache + phía người dùng
     * Ví dụ: Ba nền tảng đều có hệ thống người dùng, cần đăng nhập riêng biệt, nếu tên người dùng trùng nhau, quyền hạn trong Redis có thể xảy ra xung đột.
     *
     * @param userEnum Vai trò
     * @return Giá trị khóa cache + phía người dùng
     */
//    public String getPrefix(UserEnums userEnum) {
//        return "{" + this.name() + "_" + userEnum.name() + "}_";
//    }

    /**
     * Lấy giá trị khóa cache + phía người dùng + tiền tố tùy chỉnh
     * Ví dụ: Ba nền tảng đều có hệ thống người dùng, cần đăng nhập riêng biệt, nếu tên người dùng trùng nhau, quyền hạn trong Redis có thể xảy ra xung đột.
     *
     * @param userEnum     Vai trò
     * @param customPrefix Tiền tố tùy chỉnh
     * @return Giá trị khóa cache
     */
    public String getPrefix(String userEnum, String customPrefix) {
        return "{" + this.name() + "_" + userEnum + "}_" + customPrefix + "_";
    }
}