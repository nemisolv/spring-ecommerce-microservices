package net.nemisolv.notificationservice.kafka.consumer;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.notificationservice.kafka.identity.SendMailWithOtpToken;
import net.nemisolv.notificationservice.notification.Notification;
import net.nemisolv.notificationservice.notification.NotificationRepository;
import net.nemisolv.notificationservice.payload.RecipientInfo;
import net.nemisolv.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;
import static net.nemisolv.notificationservice.notification.NotificationType.CONFIRMATION_REGISTRATION_ACCOUNT;
import static net.nemisolv.notificationservice.notification.NotificationType.FORGOT_PASSWORD;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthNotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "confirmation-registration-user-topic")
    public void consumeConfirmationRegistrationUserNotifications(SendMailWithOtpToken sendMailWithOtpToken) throws MessagingException {
        log.info(format("Consuming the message from confirmation-registration-user-topic Topic:: %s", sendMailWithOtpToken.recipient()));
        repository.save(
                Notification.builder()
                        .type(CONFIRMATION_REGISTRATION_ACCOUNT)
                        .notificationDate(LocalDateTime.now())
                        .recipientInfo(sendMailWithOtpToken.recipient())
                        .build()
        );
        emailService.sendRegistrationConfirmationEmail(
                sendMailWithOtpToken.recipient(),
                sendMailWithOtpToken.otpTokenOptional()
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
