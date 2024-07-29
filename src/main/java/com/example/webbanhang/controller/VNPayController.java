package com.example.webbanhang.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.response.PaymentDTO;
import com.example.webbanhang.dto.response.ResponseObject;
import com.example.webbanhang.service.VNPayService;

@RestController
@RequestMapping("/api")
public class VNPayController {
    private final VNPayService paymentService;

    @Autowired
    public VNPayController(VNPayService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(
            @RequestParam("amount") long amount,
            @RequestParam(value = "bankCode", required = false) String bankCode,
            HttpServletRequest request) {
        PaymentDTO.VNPayResponse response = paymentService.createVnPayPayment(amount, bankCode, request);
        return new ResponseObject<>(HttpStatus.OK, "Success", response);
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if ("00".equals(status)) {
            return new ResponseObject<>(
                    HttpStatus.OK,
                    "Success",
                    PaymentDTO.VNPayResponse.builder()
                            .code("00")
                            .message("Success")
                            .paymentUrl("")
                            .build());
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
}
