package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MausachienthoaiResponse {
    private Long id;
    private String tenmausac;
    private String giaban;
    private String hinhanh;
}
