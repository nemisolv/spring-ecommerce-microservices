//package net.nemisolv.notificationservice.service.impl;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.nemisolv.lib.util.CommonUtil;
//import net.nemisolv.notificationservice.kafka.order.Product;
//import net.nemisolv.notificationservice.payload.RecipientInfo;
//import net.nemisolv.notificationservice.service.EmailService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static java.nio.charset.StandardCharsets.UTF_8;
//import static net.nemisolv.notificationservice.service.EmailTemplates.*;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class EmailServiceImpl implements EmailService {
//
//    private final JavaMailSender mailSender;
//    private final SpringTemplateEngine templateEngine;
//
//    @Value("${app.mail.from}")
//    private String senderEmail;
//
//    @Async
//    public void sendPaymentSuccessEmail(
//            String destinationEmail,
//            String customerName,
//            BigDecimal amount,
//            String orderReference
//    ) throws MessagingException {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
//        messageHelper.setFrom(senderEmail);
//
//        final String templateName = PAYMENT_CONFIRMATION.getTemplate();
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("customerName", customerName);
//        variables.put("amount", amount);
//        variables.put("orderReference", orderReference);
//
//        Context context = new Context();
//        context.setVariables(variables);
//        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());
//
//        try {
//            String htmlTemplate = templateEngine.process(templateName, context);
//            messageHelper.setText(htmlTemplate, true);
//
//            messageHelper.setTo(destinationEmail);
//            mailSender.send(mimeMessage);
//            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
//        } catch (MessagingException e) {
//            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
//        }
//    }
//
//    @Async
//    public void sendOrderConfirmationEmail(
//            String destinationEmail,
//            String customerName,
//            BigDecimal amount,
//            String orderReference,
//            List<Product> products
//    ) throws MessagingException {
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
//        messageHelper.setFrom(senderEmail);
//
//        final String templateName = ORDER_CONFIRMATION.getTemplate();
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("customerName", customerName);
//        variables.put("totalAmount", amount);
//        variables.put("orderReference", orderReference);
//        variables.put("products", products);
//
//        Context context = new Context();
//        context.setVariables(variables);
//        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());
//
//        try {
//            String htmlTemplate = templateEngine.process(templateName, context);
//            messageHelper.setText(htmlTemplate, true);
//
//            messageHelper.setTo(destinationEmail);
//            mailSender.send(mimeMessage);
//            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
//        } catch (MessagingException e) {
//            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
//        }
//    }
//
//    @Override
//    @Async
//    public void sendWelcomeEmail(RecipientInfo recipient) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
//        messageHelper.setFrom(senderEmail);
//
//        final String templateName = WELCOME_EMAIL.getTemplate();
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("customerName", recipient.name());
//
//        Context context = new Context();
//        context.setVariables(variables);
//        messageHelper.setSubject(WELCOME_EMAIL.getSubject());
//
//        try {
//            String htmlTemplate = templateEngine.process(templateName, context);
//            messageHelper.setText(htmlTemplate, true);
//
//            messageHelper.setTo(recipient.email());
//            mailSender.send(message);
//            log.info("INFO - Email successfully sent to {} with template {} ", recipient.email(), templateName);
//        } catch (MessagingException e) {
//            log.warn("WARNING - Cannot send Email to {} ", recipient.email());
//        }
//    }
//
//    @Override
//    @Async
//    public void sendRegistrationConfirmationEmail(RecipientInfo recipient, String token) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
//        messageHelper.setFrom(senderEmail);
//
//        final String templateName = CUSTOMER_REGISTRATION.getTemplate();
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("customerName", recipient.name());
//        String urlVerification = CommonUtil.buildEmailUrl("/verify-email", token);
//        variables.put("url", urlVerification);
//
//        Context context = new Context();
//        context.setVariables(variables);
//        messageHelper.setSubject(CUSTOMER_REGISTRATION.getSubject());
//
//        try {
//            String htmlTemplate = templateEngine.process(templateName, context);
//            messageHelper.setText(htmlTemplate, true);
//
//            messageHelper.setTo(recipient.email());
//            mailSender.send(message);
//            log.info("INFO - Email successfully sent to {} with template {} ", recipient.email(), templateName);
//        } catch (MessagingException e) {
//            log.warn("WARNING - Cannot send Email to {} ", recipient.email());
//        }
//    }
//
//    @Override
//    public void sendPasswordResetEmail(RecipientInfo recipient, String token) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
//        messageHelper.setFrom(senderEmail);
//
//        final String templateName = PASSWORD_RESET.getTemplate();
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("customerName", recipient.name());
//        String resetUrl = CommonUtil.buildEmailUrl("/reset-password", token);
//        variables.put("resetUrl", resetUrl);
//
//        Context context = new Context();
//        context.setVariables(variables);
//        messageHelper.setSubject(PASSWORD_RESET.getSubject());
//
//        try {
//            String htmlTemplate = templateEngine.process(templateName, context);
//            messageHelper.setText(htmlTemplate, true);
//
//            messageHelper.setTo(recipient.email());
//            mailSender.send(message);
//            log.info("INFO - Email successfully sent to {} with template {} ", recipient.email(), templateName);
//        } catch (MessagingException e) {
//            log.warn("WARNING - Cannot send Email to {} ", recipient.email());
//        }
//    }
//}

package net.nemisolv.notificationservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.lib.util.CommonUtil;
import net.nemisolv.notificationservice.kafka.order.Product;
import net.nemisolv.notificationservice.payload.RecipientInfo;
import net.nemisolv.notificationservice.service.EmailService;
import net.nemisolv.notificationservice.service.EmailTemplates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

    @Override

    @Async
    public void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference) {
        CompletableFuture.runAsync(() -> sendEmail(PAYMENT_CONFIRMATION, destinationEmail, customerName, amount, orderReference).block());
    }

    @Async
    public void sendOrderConfirmationEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference, List<Product> products) {
        CompletableFuture.runAsync(() -> sendEmail(ORDER_CONFIRMATION, destinationEmail, customerName, amount, orderReference, products).block());
    }

    @Async
    public void sendWelcomeEmail(RecipientInfo recipient) {
         CompletableFuture.runAsync(() -> sendEmail(WELCOME_EMAIL, recipient.email(), recipient.name()).block());
    }

    @Async
    public void sendRegistrationConfirmationEmail(RecipientInfo recipient, String token) {
        String urlVerification = CommonUtil.buildEmailUrl("/verify-email", token);
         CompletableFuture.runAsync(() -> sendEmail(CUSTOMER_REGISTRATION, recipient.email(), recipient.name(), urlVerification).block());
    }

    @Async
    public void sendPasswordResetEmail(RecipientInfo recipient, String token) {
        String resetUrl = CommonUtil.buildEmailUrl("/reset-password", token);
         CompletableFuture.runAsync(() -> sendEmail(PASSWORD_RESET, recipient.email(), recipient.name(), resetUrl).block());
    }

    private Mono<Void> sendEmail(EmailTemplates template, String destinationEmail, String customerName, Object... args) {
        return Mono.fromRunnable(() -> {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;
            try {
                messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
                messageHelper.setFrom(senderEmail);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            final String templateName = template.getTemplate();

            Map<String, Object> variables = new HashMap<>();
            variables.put("customerName", customerName);

            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i += 2) {
                    variables.put(String.valueOf(args[i]), args[i + 1]);
                }
            }

            Context context = new Context();
            context.setVariables(variables);
            try {
                messageHelper.setSubject(template.getSubject());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            try {
                String htmlTemplate = templateEngine.process(templateName, context);
                messageHelper.setText(htmlTemplate, true);
                messageHelper.setTo(destinationEmail);
                mailSender.send(mimeMessage);
                log.info("INFO - Email successfully sent to {} with template {}", destinationEmail, templateName);
            } catch (MessagingException e) {
                log.warn("WARNING - Cannot send Email to {}", destinationEmail, e);
                throw new RuntimeException("Error sending email", e);
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}

