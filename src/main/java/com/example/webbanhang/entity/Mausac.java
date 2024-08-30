package com.example.webbanhang.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

    @OneToMany(mappedBy = "mausac")
    private List<Yeuthich> yeuthichs;
}
