package net.nemisolv.notificationservice.kafka.consumer;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.lib.event.dto.NotificationEvent;
import net.nemisolv.lib.util.Constants;
import net.nemisolv.notificationservice.kafka.identity.SendMailWithOtpToken;
import net.nemisolv.notificationservice.notification.Notification;
import net.nemisolv.notificationservice.notification.NotificationRepository;
import net.nemisolv.notificationservice.payload.RecipientInfo;
import net.nemisolv.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;
import static net.nemisolv.lib.core._enum.NotificationType.CONFIRM_REGISTRATION_ACCOUNT;
import static net.nemisolv.lib.core._enum.NotificationType.FORGOT_PASSWORD;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthNotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = Constants.EventConfig.CONFIRM_REGISTRATION_TOPIC)
    public void consumeConfirmationRegistrationUserNotifications(NotificationEvent notificationEvent) throws MessagingException {
        log.info(format("Consuming the message from confirmation-registration-user-topic Topic:: %s", notificationEvent.getParams().get(Constants.EventConfig.RECIPIENT_NAME)));
        RecipientInfo recipientInfo = RecipientInfo.builder()
                .name(notificationEvent.getParams().get(Constants.EventConfig.RECIPIENT_NAME).toString())
                .email(notificationEvent.getRecipient())
                .build();

        repository.save(
                Notification.builder()
                        .type(CONFIRM_REGISTRATION_ACCOUNT)
                        .notificationDate(LocalDateTime.now())
                        .recipientInfo(
                                recipientInfo
                        )
                        .build()
        );
        emailService.sendConfirmRegistrationEmail(
                notificationEvent
        );
    }


    @KafkaListener(topics = "password-reset-email-topic")
    public void consumeForgotPasswordNotifications(SendMailWithOtpToken sendMailWithOtpToken) throws MessagingException {
        log.info(format("Consuming the message from forgot-password-email-topic Topic:: %s", sendMailWithOtpToken));
        repository.save(
                Notification.builder()
                        .type(FORGOT_PASSWORD)
                        .notificationDate(LocalDateTime.now())
                        .recipientInfo(sendMailWithOtpToken.recipient())
                        .build()
        );
        emailService.sendPasswordResetEmail(
                sendMailWithOtpToken.recipient(),
                sendMailWithOtpToken.otpTokenOptional().token()
        );
    }



}
