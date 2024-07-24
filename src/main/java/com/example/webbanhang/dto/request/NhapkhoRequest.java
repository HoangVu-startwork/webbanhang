package com.example.webbanhang.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhapkhoRequest {
    private String email;
    private String soluong;
    private String tenmausac;
    private String tensanpham;
    private LocalDate dob;
}
