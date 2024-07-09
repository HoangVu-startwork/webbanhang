package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongtinphanloaiRequest {
    private String tenphanloai;
    private String tenloaisanpham;
}
