package com.example.webbanhang.dto.response;

import jakarta.persistence.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HoadonSummaryResponse {
    private long totalOrders;
    private double totalAmount;
}
