package com.example.webbanhang.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChitiethoadonResponse {
    private Long id;
    private String dienthoaiTensanpham; // Tên sản phẩm điện thoại
    private String dienthoaiHinhanh; // Hình ảnh của sản phẩm điện thoại
    private String mausacTen; // Tên màu sắc
    private String mausacGiaban; // Giá bán theo màu sắc
    private String mausacHinhanh; // Hình ảnh theo màu sắc
    private Long mausacId;
    private Long dienthoaiId;
    private String soluong;
    private double giadienthoai;
    private double thanhgia;
    private double giatong;
    private double giamgia;
}
