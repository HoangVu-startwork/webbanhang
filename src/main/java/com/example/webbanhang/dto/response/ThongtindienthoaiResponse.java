package com.example.webbanhang.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongtindienthoaiResponse {
    private String tinhtrangmay;
    private String thietbidikem;
    private String baohanh;
    private Long dienthoaiId;
}
