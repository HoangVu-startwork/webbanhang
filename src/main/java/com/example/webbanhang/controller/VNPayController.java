package com.example.webbanhang.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.Util.VNPayUtil;
import com.example.webbanhang.config.VNPayConfig;
import com.example.webbanhang.dto.response.PaymentDTO;
import com.example.webbanhang.dto.response.ResponseObject;
import com.example.webbanhang.repository.PaymentRepository;
import com.example.webbanhang.service.HoadonService;
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
    HoadonService hoadonService;

    @Autowired
    public VNPayController(
            VNPayService paymentService,
            PaymentRepository paymentRepository,
            VNPayConfig vnPayConfig,
            HoadonService hoadonService) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
        this.vnPayConfig = vnPayConfig; // Đảm bảo VNPayConfig được tiêm
        this.hoadonService = hoadonService;
    }

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(
            @RequestParam("amount") long amount,
            @RequestParam(value = "bankCode", required = false) String bankCode,
            @RequestParam("email") String email,
            @RequestParam("diachi") String diachi,
            @RequestParam("productIds") List<Long> productIds,
            HttpServletRequest request) {

        PaymentDTO.VNPayResponse response =
                paymentService.createVnPayPayment(amount, bankCode, email, diachi, productIds, request);
        return new ResponseObject<>(HttpStatus.OK, "Success", response);
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String transactionId = request.getParameter("vnp_TxnRef");
        String vnpResponseCode = request.getParameter("vnp_ResponseCode");
        String vnpTransactionNo = request.getParameter("vnp_TransactionNo");
        String vnpOrderInfo = request.getParameter("vnp_OrderInfo");

        if ("00".equals(status)) {
            // Parse vnp_OrderInfo để lấy thông tin bổ sung
            Map<String, String> orderInfoMap = VNPayUtil.parseOrderInfo(vnpOrderInfo);

            String email = orderInfoMap.get("email");
            String diachi = orderInfoMap.get("diachi");
            List<Long> productIds = Arrays.stream(orderInfoMap.get("productIds").split(","))
                    .map(Long::parseLong)
                    .toList();
            String productIdsString = VNPayUtil.convertProductIdsToString(productIds);

            // Lưu thông tin thanh toán vào cơ sở dữ liệu
            paymentService.savePaymentInfo(
                    transactionId, vnpResponseCode, vnpTransactionNo, email, diachi, productIdsString, request);

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
