package com.example.webbanhang.controller;


import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.EmailRequest;
import com.example.webbanhang.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
