package net.nemisolv.lib.util;

import java.time.LocalDateTime;
import java.util.Date;

public class Constants {
    public static final LocalDateTime EXP_TIME_REGISTRATION_EMAIL= LocalDateTime.now().plusMinutes(15);
    public static final LocalDateTime EXP_TIME_PASSWORD_RESET_EMAIL = LocalDateTime.now().plusMinutes(15);

    public static final String CLIENT_BASE_URL = "http://localhost:3000";


}