package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhodienthoaiRequest {
    private String soluong;
    private Long mausacId;
    private String tensanpham;
}
