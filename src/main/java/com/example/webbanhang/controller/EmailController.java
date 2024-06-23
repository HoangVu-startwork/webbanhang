package com.example.webbanhang.controller;


import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.EmailRequest;
import com.example.webbanhang.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
@Controller
@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ApiResponse<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        try {
            emailService.sendSimpleMessage(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            apiResponse.setResult("Email sent successfully");
        } catch (Exception e) {
            apiResponse.setMessage(e.getMessage());
        }
        return apiResponse;
    }

    @PostMapping("/api/email/sendHtml")
    public ApiResponse<String> sendHtmlEmail(@RequestBody EmailRequest emailRequest) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        try {
            String htmlTemplate = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/emailTemplate.html")));
            emailService.sendHtmlMessage(emailRequest.getTo(), emailRequest.getSubject(), htmlTemplate);
            apiResponse.setResult("Email sent successfully");
        } catch (IOException e) {
            apiResponse.setMessage("Error reading email template: " + e.getMessage());
        } catch (MessagingException e) {
            apiResponse.setMessage("Error sending email: " + e.getMessage());
        } catch (Exception e) {
            apiResponse.setMessage("Unexpected error: " + e.getMessage());
        }
        return apiResponse;
    }
}