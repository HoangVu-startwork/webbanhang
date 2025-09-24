package com.example.webbanhang.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UudaimuahangResponse {

    private Long id;
    private String noidunguudai;
    private String dieukienuudai;
    private String phantramkhuyenmai;
    private String giakhuyenmai;
    private String dieukienthucthi;
    private Long xephanguserId;
}
