package com.example.webbanhang.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DienthoaihethongResponse {
    private Long id;
    private String tensanpham;
    private String hinhanh;
    private String hinhanhduyet;
    private String ram;
    private String bonho;
    private String giaban;
    private String tenphanloai;
    private List<MausachienthoaiResponse> mausacs;
}
