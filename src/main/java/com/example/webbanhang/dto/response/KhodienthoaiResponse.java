package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhodienthoaiResponse {
    private Long dienthoaiId;
    private String soluong;
    private Long mausacId;
}
