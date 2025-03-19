package com.example.webbanhang.dto.response;

import jakarta.persistence.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KhuyenmaiResponse {
    private Long id;
    private String phantramkhuyenmai;
    private String noidungkhuyenmai;
    private String ngaybatdau;
    private String ngayketkhuc;
    private Long dienthoaiId;
}
