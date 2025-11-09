package com.example.webbanhang.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlySummaryResponse {
    private int year;
    private int month;
    private Long totalCount;
    private Double totalAmount;
    private List<DailySummaryResponse> dailySummaries;
}
