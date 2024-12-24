package net.nemisolv.lib.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Công cụ chung
 */
public class CommonUtil {

    public static final String NUMBER_DIGITS = "0123456789";

    public static String buildEmailUrl(String prefix ,String token) {
        return Constants.CLIENT_BASE_URL +prefix+"?token="+token;

    }

    /**
     * Đổi tên bằng UUID
     *
     * @param fileName Tên tệp
     * @return Tên đã được định dạng
     */
    public static String generateUniqueFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + extension;
    }


    /**
     * Tạo số ngẫu nhiên 6 chữ số
     */
    public static String getRandomNum() {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int num = ThreadLocalRandom.current().nextInt(NUMBER_DIGITS.length());
            sb.append(NUMBER_DIGITS.charAt(num));
        }
        return sb.toString();
    }

    /**
     * Lấy chuỗi đặc biệt + 6 chữ số ngẫu nhiên
     *
     * @return
     */
    public static String generateSpecialString(String str) {
        return str + getRandomNum();
    }
}