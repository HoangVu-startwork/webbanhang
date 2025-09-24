package com.example.webbanhang.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UudaimuahangRequest {
    private String noidunguudai;
    private String dieukienuudai;
    private String phantramkhuyenmai;
    private String giakhuyenmai;
    private String dieukienthucthi;
    private Long xephanguserId;
}
