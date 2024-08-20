package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoaisanphamResponse {
    private Long id;
    private String tenloaisanpham;
    private Long danhmucId;
}
