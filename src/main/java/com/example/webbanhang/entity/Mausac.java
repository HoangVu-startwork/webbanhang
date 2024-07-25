package com.example.webbanhang.entity;

import java.util.List;

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
public class Mausac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenmausac;

    private String giaban;

    private String hinhanh;

    @ManyToOne
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;

    @OneToMany(mappedBy = "mausac")
    private List<Giohang> giohangs;

    @OneToMany(mappedBy = "mausac")
    private List<Khodienthoai> khodienthoais;

    @OneToMany(mappedBy = "mausac")
    private List<Nhapkho> nhapkhos;

    @OneToMany(mappedBy = "mausac")
    private List<Chitiethoadon> chitiethoadons;
}
