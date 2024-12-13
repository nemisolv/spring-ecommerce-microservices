package net.nemisolv.notificationservice.service;

import jakarta.mail.MessagingException;
import net.nemisolv.notificationservice.kafka.order.Product;
import net.nemisolv.notificationservice.payload.RecipientInfo;

import java.math.BigDecimal;
import java.util.List;

public interface EmailService {

    void sendWelcomeEmail(RecipientInfo recipient) throws MessagingException;
    void sendRegistrationConfirmationEmail(RecipientInfo recipient, String token) throws MessagingException;
    void sendPasswordResetEmail(RecipientInfo recipient, String token) throws MessagingException;

    void sendOrderConfirmationEmail(String email, String customerName, BigDecimal bigDecimal, String orderReference, List<Product> products) throws MessagingException;

    void sendPaymentSuccessEmail(String email, String customerName, BigDecimal amount, String s1) throws MessagingException;
}