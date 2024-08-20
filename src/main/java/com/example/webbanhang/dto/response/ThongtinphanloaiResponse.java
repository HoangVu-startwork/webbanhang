package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongtinphanloaiResponse {
    private Long id;
    private String tenphanloai;
    private Long loaisanphamId;
}
