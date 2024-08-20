package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiohangRequest {
    private String email;
    private String soluong;
    private String tenmausac;
    private String tensanpham;
    private Long dienthoaiId;
}
