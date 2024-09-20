package com.example.webbanhang.dto.request;

import java.util.List;

import com.example.webbanhang.entity.Mausac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DienthoaiphanloaiRequest {
    private Long id;
    private String tensanpham;
    private String hinhanh;
    private String hinhanhduyet;
    private String ram;
    private String bonho;
    private String giaban;
    private String tenphanloai;
    private List<Mausac> mausacs;
}
