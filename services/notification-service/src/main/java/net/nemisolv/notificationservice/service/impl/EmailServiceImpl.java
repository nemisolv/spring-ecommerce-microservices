package net.nemisolv.notificationservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.lib.event.dto.NotificationEvent;
import net.nemisolv.lib.util.CommonUtil;
import net.nemisolv.lib.util.Constants;
import net.nemisolv.notificationservice.kafka.order.Product;
import net.nemisolv.notificationservice.payload.RecipientInfo;
import net.nemisolv.notificationservice.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static net.nemisolv.notificationservice.service.EmailTemplates.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${app.mail.from}")
    private String senderEmail;

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom(senderEmail);

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom(senderEmail);

        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }
    }

    @Override
    @Async
    public void sendWelcomeEmail(RecipientInfo recipient) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom(senderEmail);

        final String templateName = WELCOME_EMAIL.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", recipient.name());

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(WELCOME_EMAIL.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(recipient.email());
            mailSender.send(message);
            log.info("INFO - Email successfully sent to {} with template {} ", recipient.email(), templateName);
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", recipient.email());
        }
    }

    @Async
    public void sendConfirmRegistrationEmail(NotificationEvent notificationEvent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom(senderEmail);

        final String templateName = ACCOUNT_REGISTRATION.getTemplate();

        RecipientInfo recipient = RecipientInfo.builder()
                .name(notificationEvent.getParams().get(Constants.EventConfig.RECIPIENT_NAME).toString())
                .email(notificationEvent.getRecipient())
                .build();

        String token = notificationEvent.getParams().get(Constants.EventConfig.TOKEN_URL).toString();
        String otp = notificationEvent.getParams().get(Constants.EventConfig.OTP).toString();


        Map<String, Object> variables = new HashMap<>();
        variables.put("recipientName", recipient.name());
        String urlVerification = CommonUtil.buildEmailUrl("/auth/verify-email/request", token);
        variables.put("url", urlVerification);
        variables.put("otp", otp);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ACCOUNT_REGISTRATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(recipient.email());
            mailSender.send(message);
            log.info("INFO - Email successfully sent to {} with template {} ", recipient.email(), templateName);
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", recipient.email());
        }
    }

    @Override
    public void sendPasswordResetEmail(RecipientInfo recipient, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom(senderEmail);

        final String templateName = PASSWORD_RESET.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("recipientName", recipient.name());
        String resetUrl = CommonUtil.buildEmailUrl("/auth/reset-password/request/", token);
        variables.put("url", resetUrl);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PASSWORD_RESET.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(recipient.email());
            mailSender.send(message);
            log.info("INFO - Email successfully sent to {} with template {} ", recipient.email(), templateName);
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", recipient.email());
        }
    }
}