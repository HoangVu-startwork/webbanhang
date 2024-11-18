package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhapkhosResponse {
    private Long dienthoaiId;
    private String soluong;
    private Long mausacId;
    private String userId;
    private String dob;
    private String tenSanPham; // Thêm trường này để hiển thị tên điện thoại
    private String tenMauSac; // Thêm trường này để hiển thị tên màu sắc
}
