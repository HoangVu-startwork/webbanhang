package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webbanhang.Util.VNPayUtil;
import com.example.webbanhang.config.VNPayConfig;
import com.example.webbanhang.dto.request.HoadonRequest;
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

    HoadonService hoadonService;

    private final VNPayUtil vnPayUtil;

    @Autowired
    public VNPayService(
            VNPayConfig vnPayConfig,
            PaymentRepository paymentRepository,
            VNPayUtil vnPayUtil,
            HoadonService hoadonService) {
        this.vnPayConfig = vnPayConfig;
        this.paymentRepository = paymentRepository;
        this.vnPayUtil = vnPayUtil;
        this.hoadonService = hoadonService;
    }

    public PaymentDTO.VNPayResponse createVnPayPayment(
            long amount,
            String bankCode,
            String email,
            String diachi,
            List<Long> productIds,
            HttpServletRequest request) {

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount * 100));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        String ipAddress = VNPayUtil.getIpAddress(request);
        vnpParamsMap.put("vnp_IpAddr", ipAddress);

        // Tạo chuỗi vnp_OrderInfo chứa các thông tin bổ sung
        String orderInfo = String.format(
                "email=%s|diachi=%s|productIds=%s",
                email, diachi, productIds.toString().replaceAll("[\\[\\] ]", ""));
        vnpParamsMap.put("vnp_OrderInfo", orderInfo);

        // Build query URL
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        log.info("Generated VNPay payment URL: {}", paymentUrl);

        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

    @Transactional
    public void savePaymentInfo(
            String transactionId,
            String vnpResponseCode,
            String vnpTransactionNo,
            String email,
            String diachi,
            String productIdsString,
            HttpServletRequest request) {

        String bankCode = request.getParameter("vnp_BankCode");
        String cardType = request.getParameter("vnp_CardType");
        long amount = Long.parseLong(request.getParameter("vnp_Amount")) / 100;
        String vnpOrderInfo = request.getParameter("vnp_OrderInfo");

        String mahd = vnPayUtil.generateUniqueMaHD();
        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .vnpResponseCode(vnpResponseCode)
                .vnpTransactionNo(vnpTransactionNo)
                .bankCode(bankCode)
                .mahd(mahd)
                .cardType(cardType)
                .amount(amount)
                .createDate(LocalDateTime.now())
                .vnpOrderInfo(vnpOrderInfo)
                .email(email)
                .diachi(diachi)
                .productIds(productIdsString)
                .build();

        paymentRepository.save(payment);

        List<Long> productIds =
                Arrays.stream(productIdsString.split(",")).map(Long::parseLong).toList();

        HoadonRequest hoadonRequest = HoadonRequest.builder()
                .mahd(mahd)
                .email(email)
                .diachi(diachi)
                .mahd(mahd)
                .transactionId(transactionId)
                .productIds(productIds)
                .build();

        hoadonService.createHoadon1(hoadonRequest);
    }
}
