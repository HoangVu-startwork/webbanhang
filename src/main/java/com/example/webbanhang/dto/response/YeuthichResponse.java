package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YeuthichResponse {
    private Long id;
    private Long dienthoaiId;
    private Long mausacId;
    private String userId;
}
