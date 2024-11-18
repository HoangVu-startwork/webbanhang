package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuonghieudienthoaisResponse {
    private Long id;
    private String tenthuonghieu;
    private String hinhanh;
    private Long dienthoaiId;
    private String tenSanPham;
    private String tinhtrang;
    private String dob;
}
