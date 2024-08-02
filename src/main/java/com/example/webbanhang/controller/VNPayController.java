package com.example.webbanhang.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.config.VNPayConfig;
import com.example.webbanhang.dto.response.PaymentDTO;
import com.example.webbanhang.dto.response.ResponseObject;
import com.example.webbanhang.repository.PaymentRepository;
import com.example.webbanhang.service.VNPayService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class VNPayController {
    private final VNPayService paymentService;
    PaymentRepository paymentRepository;
    VNPayConfig vnPayConfig;

    @Autowired
    public VNPayController(VNPayService paymentService, PaymentRepository paymentRepository, VNPayConfig vnPayConfig) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
        this.vnPayConfig = vnPayConfig; // Đảm bảo VNPayConfig được tiêm
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
        String transactionId = request.getParameter("vnp_TxnRef");
        String vnpResponseCode = request.getParameter("vnp_ResponseCode");
        String vnpTransactionNo = request.getParameter("vnp_TransactionNo");

        if ("00".equals(status)) {
            // Lưu thông tin thanh toán vào cơ sở dữ liệu
            paymentService.savePaymentInfo(transactionId, vnpResponseCode, vnpTransactionNo, request);

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
