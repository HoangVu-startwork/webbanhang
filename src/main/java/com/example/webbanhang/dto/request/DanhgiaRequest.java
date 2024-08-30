package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhgiaRequest {
    private Long id;
    private String chitiet;
    private double diem;
    private String dob;
    private Long dienthoaiId;
    private String userId;
}
