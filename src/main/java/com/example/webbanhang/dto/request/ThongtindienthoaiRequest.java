package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongtindienthoaiRequest {
    private String tinhtrangmay;
    private String thietbidikem;
    private String baohanh;
    private String tensanpham;
}
