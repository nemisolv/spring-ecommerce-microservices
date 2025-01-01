package net.nemisolv.lib.util;

import net.nemisolv.lib.core._enum.EmailTemplates;
import net.nemisolv.lib.core._enum.NotificationType;
import net.nemisolv.lib.event.dto.NotificationEvent;

public class EventUtils {
    public static EmailTemplates mapEventTypeToMailTemplate(NotificationType notificationType) {
        switch (notificationType) {
            case CONFIRM_REGISTRATION_ACCOUNT ->  {
                return EmailTemplates.CONFIRM_REGISTRATION_ACCOUNT;
            }
            case PASSWORD_RESET -> {
                return EmailTemplates.PASSWORD_RESET;
            }
            case PAYMENT_CONFIRMATION -> {
                return EmailTemplates.PAYMENT_CONFIRMATION;
            }
            default -> {
                return null;
            }
        }
        
    }

    public static Object getEventParam(NotificationEvent event, String key) {
        return event.getParams().get(key);
    }


}
