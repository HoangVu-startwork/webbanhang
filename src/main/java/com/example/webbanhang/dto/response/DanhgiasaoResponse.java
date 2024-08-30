package com.example.webbanhang.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhgiasaoResponse {
    private Long dienthoaiId;
    private double namsao;
    private double bonsao;
    private double basao;
    private double haisao;
    private double motsao;
    private double tongsao;
}
