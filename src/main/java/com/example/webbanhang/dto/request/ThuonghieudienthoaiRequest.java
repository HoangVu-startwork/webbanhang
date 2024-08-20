package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuonghieudienthoaiRequest {
    private String tenthuonghieu;

    private String hinhanh;
    private Long dienthoaiId;
}
