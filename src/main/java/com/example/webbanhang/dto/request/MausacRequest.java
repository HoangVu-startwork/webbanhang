package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MausacRequest {
    private Long id;
    private String tenmausac;
    private String giaban;
    private String hinhanh;
    private String tensanpham;
}
