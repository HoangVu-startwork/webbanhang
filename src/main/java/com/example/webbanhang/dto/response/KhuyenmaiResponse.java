package com.example.webbanhang.dto.response;

import jakarta.persistence.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhuyenmaiResponse {
    private String phantramkhuyenmai;
    private String noidungkhuyenmai;
    private String ngaybatdau;
    private String ngayketkhuc;
    private Long dienthoaiId;
}
