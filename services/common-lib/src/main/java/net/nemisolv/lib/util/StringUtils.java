package net.nemisolv.lib.util;


import cn.hutool.core.util.StrUtil;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils extends StrUtil {



    /**
     * Cắt chuỗi, nếu vượt quá độ dài thì cắt
     *
     * @param var  Chuỗi
     * @param size Độ dài
     * @return Chuỗi được xử lý
     */
    public static String subStringLength(String var, Integer size) {
        // Kiểm tra xem độ dài của chuỗi var có lớn hơn size hay không
        if (var.length() > size) {
            return var.substring(0, (size - 4)) + "...";
        }
        // Nếu độ dài chuỗi var nhỏ hơn hoặc bằng size, trả về chuỗi var nguyên bản
        return var;
    }

    /**
     * Chuyển đổi đối tượng thành map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        // Kiểm tra xem đối tượng có null hay không
        if (obj == null) {
            return null;
        }
        // Khởi tạo Map<String, Object> với kích thước ban đầu là 16
        Map<String, Object> map = new HashMap<String, Object>(16);
        // Lấy thông tin BeanInfo của lớp của object
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        // Lấy danh sách các PropertyDescriptor của BeanInfo
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        // Duyệt qua từng PropertyDescriptor
        for (PropertyDescriptor property : propertyDescriptors) {
            // Lấy tên của thuộc tính
            String key = property.getName();
            // Nếu tên thuộc tính là "class" (không phân biệt chữ hoa chữ thường)
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            // Gọi phương thức getter để lấy giá trị của thuộc tính
            Object value = getter != null ? getter.invoke(obj) : null;
            // Thêm cặp key-value vào Map
            map.put(key, value);
        }
        // Trả về Map<String, Object> đã được tạo
        return map;
    }


    /**
     * Chuyển đổi camel case thành dấu gạch dưới
     */
    public static String camel2Underline(String str) {

        // Kiểm tra xem chuỗi có rỗng hay không
        if (StrUtil.isBlank(str)) {
            return "";
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        // Khởi tạo StringBuffer để lưu trữ chuỗi kết quả
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                sb.append("_" + Character.toLowerCase(str.charAt(i)));
            } else {
                // Nối thêm ký tự hiện tại vào StringBuffer
                sb.append(str.charAt(i));
            }
        }
        // Nối ký tự đầu tiên của chuỗi str với chuỗi kết quả và chuyển đổi thành chữ thường
        return (str.charAt(0) + sb.toString()).toLowerCase();
    }

    /**
     * Nếu chuỗi {@code str} đã cho không chứa {@code appendStr}, thì nối {@code appendStr} vào cuối {@code str};
     * Nếu đã chứa {@code appendStr}, thì nối {@code otherwise} vào cuối {@code str}.
     *
     * @param str       Chuỗi đã cho
     * @param appendStr Nội dung cần nối
     * @param otherwise Nội dung nối vào cuối {@code str} khi {@code appendStr} không thỏa mãn
     * @return Chuỗi đã nối
     */
    public static String appendIfNotContain(String str, String appendStr, String otherwise) {
        // Kiểm tra xem chuỗi str hoặc appendStr có rỗng hay không
        if (isEmpty(str) || isEmpty(appendStr)) {
            return str;
        }
        // Kiểm tra xem chuỗi str có chứa chuỗi appendStr hay không
        if (str.contains(appendStr)) {
            return str.concat(otherwise);
        }
        return str.concat(appendStr);
    }

    /**
     * Cắt chuỗi
     *
     * @param str    Chuỗi
     * @param length Độ dài
     * @return Chuỗi được xử lý
     */
    public static String sub(String str, Integer length) {
        // Kiểm tra xem độ dài của chuỗi str có nhỏ hơn length hay không
        if (str.length() < length) {
            return str;
        }
        return str.substring(0, length);
    }

    /**
     * Lọc các ký tự đặc biệt
     *
     * @param str
     * @return
     */
    public static String filterSpecialChart(String str) {
        // Khai báo chuỗi regular expression (regEx) để xác định các ký tự đặc biệt cần lọc bỏ
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        // Biên dịch regular expression thành Pattern
        Pattern p = Pattern.compile(regEx);
        // Tạo Matcher để tìm kiếm các ký tự đặc biệt trong chuỗi str
        Matcher m = p.matcher(str);
        // Thay thế tất cả các ký tự đặc biệt bằng chuỗi rỗng và loại bỏ khoảng trắng thừa
        return m.replaceAll("").trim();
    }

    /**
     * double to price string
     *
     * @return
     */
    public static String toFen(Double doubleValue) {
        // Chuyển đổi số double thành chuỗi
        String str = doubleValue.toString();
        // Nếu chuỗi str không chứa dấu "."
        if (!str.contains(".")) {
            // Thêm ".00" vào cuối chuỗi
            str = str + ".00";
        } else if (str.substring(str.indexOf(".")).length() == 2) {
            // Thêm "0" vào cuối chuỗi
            str = str + "0";
        }
        return str;
    }

}