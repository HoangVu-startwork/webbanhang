package com.example.webbanhang.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySummaryResponse {
    private String date;
    private Long count;
    private Double total;
}
