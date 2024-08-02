package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webbanhang.Util.VNPayUtil;
import com.example.webbanhang.config.VNPayConfig;
import com.example.webbanhang.dto.response.PaymentDTO;
import com.example.webbanhang.entity.Payment;
import com.example.webbanhang.repository.PaymentRepository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VNPayService {

    private final VNPayConfig vnPayConfig;
    PaymentRepository paymentRepository;

    @Autowired
    public VNPayService(VNPayConfig vnPayConfig, PaymentRepository paymentRepository) {
        this.vnPayConfig = vnPayConfig;
        this.paymentRepository = paymentRepository;
    }

    public PaymentDTO.VNPayResponse createVnPayPayment(long amount, String bankCode, HttpServletRequest request) {
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount * 100)); // VNPay expects amount in cents
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        String ipAddress = VNPayUtil.getIpAddress(request);
        vnpParamsMap.put("vnp_IpAddr", ipAddress);

        // Build query URL
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        // Log the generated payment URL
        log.info("Generated VNPay payment URL: {}", paymentUrl);

        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    public void savePaymentInfo(
            String transactionId, String vnpResponseCode, String vnpTransactionNo, HttpServletRequest request) {
        String bankCode = request.getParameter("vnp_BankCode");
        String cardType = request.getParameter("vnp_CardType"); // Nếu có
        long amount = Long.parseLong(request.getParameter("vnp_Amount"));
        String vnpOrderInfo = request.getParameter("vnp_OrderInfo"); // Lấy thông tin order từ request

        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .vnpResponseCode(vnpResponseCode)
                .vnpTransactionNo(vnpTransactionNo)
                .bankCode(bankCode)
                .cardType(cardType)
                .amount(amount / 100)
                .createDate(LocalDateTime.now())
                .vnpOrderInfo(vnpOrderInfo) // Thêm thông tin order vào entity
                .build();

        paymentRepository.save(payment);
    }
}
