package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuonghieumenuResponse {
    private Long id;

    private String hinhanh;
    private String label;
    private String text;
    private Long dienthoaiId;
}
