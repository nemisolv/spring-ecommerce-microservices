package net.nemisolv.identity.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.identity.payload.auth.SendMailWithToken;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthProducer {

    private final KafkaTemplate<String, SendMailWithToken> kafkaTemplate;

    public void sendConfirmationRegistrationUser(SendMailWithToken sendMailWithToken) {
        log.info("Sending confirmation registration user");
        Message<SendMailWithToken> message = MessageBuilder
                .withPayload(sendMailWithToken)
                .setHeader(TOPIC, "confirmation-registration-user-topic")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendForgotPasswordEmail(SendMailWithToken sendMailWithToken) {
        log.info("Sending forgot password email");
        Message<SendMailWithToken> message = MessageBuilder
                .withPayload(sendMailWithToken)
                .setHeader(TOPIC, "forgot-password-email-topic")
                .build();
    }

}
