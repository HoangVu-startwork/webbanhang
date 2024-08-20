package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DienthoaiResponse {
    private Long id;
    private String tensanpham;
    private String hinhanh;
    private String hinhanhduyet;
    private String ram;
    private String bonho;
    private String giaban;
    private Long thongtinphanloaiId;
}
