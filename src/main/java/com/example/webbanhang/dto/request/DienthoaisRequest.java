package com.example.webbanhang.dto.request;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DienthoaisRequest {
    private String tensanpham;
    private String hinhanh;
    private String hinhanhduyet;
    private String ram;
    private String bonho;
    private String giaban;
    private Long thongtinphanloaiId;
    private String tinhtrang;
}
