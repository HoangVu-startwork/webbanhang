package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhmucResponse {
    private Long id;
    private String tendanhmuc;
    private String hinhanh;
    private Long muclucId;
    private Long hedieuhanhId;
}
