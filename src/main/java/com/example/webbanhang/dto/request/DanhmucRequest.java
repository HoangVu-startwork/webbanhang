package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhmucRequest {
    private String tendanhmuc;
    private String hinhanh;
    private String tenmucluc;
    private String tenhedieuhanh;
}
