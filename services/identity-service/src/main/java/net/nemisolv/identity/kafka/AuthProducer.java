package net.nemisolv.identity.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.lib.event.dto.NotificationEvent;
import net.nemisolv.lib.util.Constants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void sendConfirmRegistrationAccount(NotificationEvent notificationEvent) {
        log.info("Sending confirmation registration user");
        Message<NotificationEvent> message = MessageBuilder
                .withPayload(notificationEvent)
                .setHeader(TOPIC, Constants.EventConfig.CONFIRM_REGISTRATION_TOPIC)
                .build();
        kafkaTemplate.send(message);
    }

    public void sendForgotPasswordEmail(NotificationEvent notificationEvent) {
        log.info("Sending forgot password email");
        Message<NotificationEvent> message = MessageBuilder
                .withPayload(notificationEvent)
                .setHeader(TOPIC, "password-reset-email-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
