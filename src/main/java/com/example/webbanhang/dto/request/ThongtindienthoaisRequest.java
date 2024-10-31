package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongtindienthoaisRequest {
    private String tinhtrangmay;
    private String thietbidikem;
    private String baohanh;
    private Long dienthoaiId;
}
