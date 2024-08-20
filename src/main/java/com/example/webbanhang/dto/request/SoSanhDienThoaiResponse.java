package com.example.webbanhang.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoSanhDienThoaiResponse {
    private Long id1;
    private Long id2;
    private String tenSanPham1;
    private String tenSanPham2;
    private String ram1;
    private String ram2;
    private String boNho1;
    private String boNho2;
    private String kichThuocManHinh1;
    private String kichThuocManHinh2;
    private String giaBan1;
    private String giaBan2;

    // Comparison results
    private String ramComparison;
    private String boNhoComparison;
    private String kichThuocManHinhComparison;
    private String giaBanComparison;
}
