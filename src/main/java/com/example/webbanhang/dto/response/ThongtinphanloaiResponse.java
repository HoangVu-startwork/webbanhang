package com.example.webbanhang.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongtinphanloaiResponse {
    private Long id;
    private String tenphanloai;
    private Long loaisanphamId;
}
