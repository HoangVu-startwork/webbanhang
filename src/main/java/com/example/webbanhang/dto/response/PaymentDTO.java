package com.example.webbanhang.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PaymentDTO {

    @Builder
    @Getter
    @Setter
    public static class VNPayResponse {
        private String code;
        private String message;
        private String paymentUrl;
    }
}
