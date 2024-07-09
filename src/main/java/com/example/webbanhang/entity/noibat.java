package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Noibat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String noidung;

    private String thutu;

    private String trangthai;

    private String ngaybatdau;

    private String ngayketkhuc;

    @ManyToOne
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;
}
