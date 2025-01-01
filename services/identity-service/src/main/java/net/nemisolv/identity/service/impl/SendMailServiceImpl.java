package net.nemisolv.identity.service.impl;

import lombok.RequiredArgsConstructor;
import net.nemisolv.identity.entity.ConfirmationEmail;
import net.nemisolv.identity.kafka.AuthProducer;
import net.nemisolv.identity.repository.ConfirmationEmailRepository;
import net.nemisolv.identity.service.SendMailService;
import net.nemisolv.lib.core._enum.MailType;
import net.nemisolv.lib.core._enum.NotificationType;
import net.nemisolv.lib.event.dto.NotificationEvent;
import net.nemisolv.lib.util.CommonUtil;
import net.nemisolv.lib.util.Constants;
import net.nemisolv.lib.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static net.nemisolv.lib.util.Constants.getExpTimeRegistrationEmail;

@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService
{

    private final AuthProducer authProducer;
    private final ConfirmationEmailRepository confirmationEmailRepo;

    @Value("${app.secure.auth_secret}")
    private String authSecret;
    @Override
    public void sendEmailConfirmation(String email, String recipientName) {
        String otp = CommonUtil.getRandomNum();  // 6 digits
        String token = CryptoUtil.sha256Hash(otp + authSecret);

        saveConfirmationEmail(token, email);

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel(Constants.EventConfig.EMAIL_CHANNEL_NAME)
                .recipient(email)
                .notificationType(NotificationType.CONFIRM_REGISTRATION_ACCOUNT)
                .params(Map.of(
                        Constants.EventConfig.RECIPIENT_NAME, recipientName,
                        Constants.EventConfig.TOKEN_URL, CommonUtil.buildEmailUrl("/auth/verify-email/request", token),
                        Constants.EventConfig.OTP, otp
                ))
                .build();

        authProducer.sendConfirmRegistrationAccount(notificationEvent);
    }


    private void saveConfirmationEmail(String token,String identifier) {


        // revoke all previous confirmation email
        confirmationEmailRepo.findByTypeAndIdentifier(MailType.REGISTRATION_CONFIRMATION, identifier)
                .forEach(confirmationEmail -> {
                    confirmationEmail.setRevoked(true);
                    confirmationEmailRepo.save(confirmationEmail);
                });

        ConfirmationEmail confirmationEmail = ConfirmationEmail.builder()
                .token(token)
                .type(MailType.REGISTRATION_CONFIRMATION)
                .expiredAt(getExpTimeRegistrationEmail())
                .identifier(identifier)
                .build();

        confirmationEmailRepo.save(confirmationEmail);
    }
}
