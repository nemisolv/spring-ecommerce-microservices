package net.nemisolv.identity.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nemisolv.identity.payload.auth.SendMailWithOtpToken;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthProducer {

    private final KafkaTemplate<String, SendMailWithOtpToken> kafkaTemplate;

    public void sendConfirmationRegistrationUser(SendMailWithOtpToken sendMailWithOtpToken) {
        log.info("Sending confirmation registration user");
        Message<SendMailWithOtpToken> message = MessageBuilder
                .withPayload(sendMailWithOtpToken)
                .setHeader(TOPIC, "confirmation-registration-user-topic")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendForgotPasswordEmail(SendMailWithOtpToken sendMailWithOtpToken) {
        log.info("Sending forgot password email");
        Message<SendMailWithOtpToken> message = MessageBuilder
                .withPayload(sendMailWithOtpToken)
                .setHeader(TOPIC, "password-reset-email-topic")
                .build();
        kafkaTemplate.send(message);
    }

}
