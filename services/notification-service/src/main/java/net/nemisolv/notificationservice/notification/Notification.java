package net.nemisolv.notificationservice.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.nemisolv.notificationservice.kafka.order.OrderConfirmation;
import net.nemisolv.notificationservice.kafka.payment.PaymentConfirmation;
import net.nemisolv.notificationservice.payload.RecipientInfo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private String title;
    private String content;
    private LocalDateTime notificationDate;
    private Map<String, Object> details;
    private RecipientInfo recipientInfo;
}