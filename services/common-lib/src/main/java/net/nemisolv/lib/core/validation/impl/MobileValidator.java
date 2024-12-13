package net.nemisolv.lib.core.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.nemisolv.techshop.core.validation.Mobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private static final Pattern MOBILE_PHONE_PATTERN = Pattern.compile("^0?(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$");
    private static final Pattern AREA_PHONE_PATTERN = Pattern.compile("0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|13[0-9]\\d{8}|15[1089]\\d{8}|16[2-7]\\d{8}|17[6-8]\\d{8}|18[0-9]\\d{8}");
    private static final Pattern SHORT_PHONE_PATTERN = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = null;
        // Xác thực số điện thoại di động
        if (value.length() == 11) {
            matcher = MOBILE_PHONE_PATTERN.matcher(value);
            // Xác thực số điện thoại cố định có mã vùng
        } else if (value.length() > 9) {
            matcher = AREA_PHONE_PATTERN.matcher(value);
            // Xác thực số điện thoại cố định không có mã vùng
        } else {
            matcher = SHORT_PHONE_PATTERN.matcher(value);
        }
        return matcher.matches();
    }


    @Override
    public void initialize(Mobile constraint) {
        // Không cần thực hiện gì trong phương thức này
    }
}