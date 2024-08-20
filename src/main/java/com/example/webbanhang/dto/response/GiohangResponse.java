package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiohangResponse {
    private Long dienthoaiId;
    private String tensanpham;
    private Long mausacId;
    private String tenmausac;
    private String soluong;
    private String userId;
    private String mausacGiaban;
    private String phantramKhuyenmai;
}
