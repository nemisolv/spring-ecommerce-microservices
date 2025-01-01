package net.nemisolv.lib.util;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

public class Constants {
//    public static final int EXP_TIME_REGISTRATION_EMAIL_MINUTES= 15
//    public static final int EXP_TIME_PASSWORD_RESET_EMAIL =



    public static LocalDateTime getExpTimeRegistrationEmail() {
        return LocalDateTime.now().plusMinutes(15);
    }

    public static LocalDateTime getExpTimePasswordResetEmail() {
        return LocalDateTime.now().plusMinutes(15);
    }

    public static final String CLIENT_BASE_URL = "http://localhost:3000";

    @Getter
    public static final class EventConfig {
    public static final String EMAIL_CHANNEL_NAME = "EMAIL";
    public static final String RECIPIENT_NAME = "recipientName";
    public static final String TOKEN_URL = "tokenUrl";
    public static final String OTP = "otp";

    public static  final String CONFIRM_REGISTRATION_TOPIC = "confirm-registration-user-topic";
}


}