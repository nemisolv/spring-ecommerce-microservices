package net.nemisolv.lib.util;

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


}