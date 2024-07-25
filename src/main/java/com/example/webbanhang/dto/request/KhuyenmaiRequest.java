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
public class KhuyenmaiRequest {
    private String phantramkhuyenmai;
    private String noidungkhuyenmai;
    private String ngaybatdau;
    private String ngayketkhuc;
    private String tensanpham;
}
