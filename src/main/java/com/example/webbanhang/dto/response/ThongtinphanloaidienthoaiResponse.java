package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongtinphanloaidienthoaiResponse {
    private Long id;
    private String tenphanloai;
}
