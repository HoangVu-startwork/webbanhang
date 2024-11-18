package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuonghieumenusResponse {
    private Long id;
    private String hinhanh;
    private String label;
    private String text;
    private String tinhtrang;
    private Long dienthoaiId;
    private String dob;
    private String tenSanPham;
}
