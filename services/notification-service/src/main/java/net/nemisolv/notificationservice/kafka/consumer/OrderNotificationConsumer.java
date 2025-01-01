package net.nemisolv.notificationservice.kafka.consumer;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.notificationservice.kafka.order.OrderConfirmation;
import net.nemisolv.notificationservice.kafka.payment.PaymentConfirmation;
import net.nemisolv.notificationservice.notification.Notification;
import net.nemisolv.notificationservice.notification.NotificationRepository;
import net.nemisolv.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static java.lang.String.format;
import static net.nemisolv.lib.core._enum.NotificationType.PASSWORD_RESET;
import static net.nemisolv.lib.core._enum.NotificationType.PAYMENT_CONFIRMATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderNotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
        repository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .details(Map.of("paymentConfirmation", paymentConfirmation))
                        .build()
        );
        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-created-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from order-created-topic Topic:: %s", orderConfirmation));
        repository.save(
                Notification.builder()
                        .type(PASSWORD_RESET)
                        .notificationDate(LocalDateTime.now())
                        .details(Map.of("orderConfirmation", orderConfirmation))
                        .build()
        );
        var customerName = orderConfirmation.customer().name();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}