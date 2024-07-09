package com.example.webbanhang.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhmucResponse {
    private Long id;
    private String tendanhmuc;
    private String hinhanh;
    private Long muclucId;
    private Long hedieuhanhId;
}
