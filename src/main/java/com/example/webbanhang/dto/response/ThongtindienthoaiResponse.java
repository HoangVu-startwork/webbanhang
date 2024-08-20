package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongtindienthoaiResponse {
    private String tinhtrangmay;
    private String thietbidikem;
    private String baohanh;
    private Long dienthoaiId;
}
