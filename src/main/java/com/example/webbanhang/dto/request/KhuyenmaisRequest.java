package com.example.webbanhang.dto.request;

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
public class KhuyenmaisRequest {
    private String phantramkhuyenmai;
    private String noidungkhuyenmai;
    private String ngaybatdau;
    private String ngayketkhuc;
    private Long dienthoaiId;
}
