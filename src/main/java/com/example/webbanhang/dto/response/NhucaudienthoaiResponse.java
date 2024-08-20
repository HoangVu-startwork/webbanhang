package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhucaudienthoaiResponse {
    private Long id;
    private String tennhucau;
    private String hinhanh;
    private String mausac;
}
