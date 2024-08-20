package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhucaudienthoaiRequest {
    private Long id;
    private String tennhucau;
    private String hinhanh;
    private String mausac;
}