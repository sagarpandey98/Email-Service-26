package org.example.emailservice.consumers;

import org.example.emailservice.dtos.SendEmailEventDto;
import org.example.emailservice.utils.EmailUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;

@Component
public class SendEmailEventConsumer {

    private final EmailUtil emailUtil;
    private final ObjectMapper objectMapper;

    public SendEmailEventConsumer(EmailUtil emailUtil, ObjectMapper objectMapper) {
        this.emailUtil = emailUtil;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendEmail", groupId = "emailService")
    public void consumeSendEmailEvent(String message) {
        try {
            SendEmailEventDto event = objectMapper.readValue(message, SendEmailEventDto.class);
            emailUtil.sendEmail(event.getTo(), event.getFrom(), event.getSubject(), event.getBody());
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
    }
}