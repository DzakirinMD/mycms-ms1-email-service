package com.mycmsms1emailservice.controller;

import com.mycmsms1emailservice.domain.EmailDetails;
import com.mycmsms1emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email-service")
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public  String sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendSimpleEmail(emailDetails);
    }
}
