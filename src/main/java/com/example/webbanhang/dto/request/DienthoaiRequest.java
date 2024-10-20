package com.example.webbanhang.dto.request;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DienthoaiRequest {
    private String tensanpham;
    private String hinhanh;
    private String hinhanhduyet;
    private String ram;
    private String bonho;
    private String giaban;
    private String tenphanloai;
    private String tinhtrang;
}
