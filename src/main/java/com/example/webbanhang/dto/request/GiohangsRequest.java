package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiohangsRequest {
    private String userId;
    private String soluong;
    private Long mausacId;
    private Long dienthoaiId;
}
