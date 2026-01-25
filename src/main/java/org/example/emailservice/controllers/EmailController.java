package org.example.emailservice.controllers;

import org.example.emailservice.dtos.SendEmailEventDto;
import org.example.emailservice.utils.EmailUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailUtil emailUtil;

    public EmailController(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailEventDto emailRequest) {
        try {
            emailUtil.sendEmail(emailRequest.getTo(), emailRequest.getFrom(), emailRequest.getSubject(), emailRequest.getBody());
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().body("Failed to send email: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("EmailService is running!");
    }
}