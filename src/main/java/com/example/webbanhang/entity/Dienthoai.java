package com.example.webbanhang.entity;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Dienthoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tensanpham;

    private String hinhanh;

    @Column(columnDefinition = "TEXT")
    private String hinhanhduyet;

    // Custom getter to convert string to list
    public List<String> getHinhanhduyetAsList() {
        return Arrays.asList(hinhanhduyet.split(","));
    }

    // Custom setter to convert list to string
    public void setHinhanhduyetFromList(List<String> hinhanhduyetList) {
        this.hinhanhduyet = String.join(",", hinhanhduyetList);
    }

    private String ram;

    private String bonho;

    private String giaban;

    private String tinhtrang;

    @ManyToOne
    @JoinColumn(name = "thongtinphanloai_id")
    private Thongtinphanloai thongtinphanloai;

    @OneToMany(mappedBy = "dienthoai")
    private List<Khuyenmai> khuyenmais;

    @OneToMany(mappedBy = "dienthoai", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mausac> mausacs;

    @OneToMany(mappedBy = "dienthoai")
    private List<Thongtindienthoai> thongtindienthoais;

    @OneToMany(mappedBy = "dienthoai")
    private List<Noibat> noibats;

    @OneToMany(mappedBy = "dienthoai")
    private List<Thongsokythuat> thongsokythuats;

    @OneToMany(mappedBy = "dienthoai")
    private List<Giohang> giohangs;

    @OneToMany(mappedBy = "dienthoai")
    private List<Khodienthoai> khodienthoais;

    @OneToMany(mappedBy = "dienthoai")
    private List<Nhapkho> nhapkhos;

    @OneToMany(mappedBy = "dienthoai")
    private List<Thuonghieumenu> thuonghieumenus;

    @OneToMany(mappedBy = "dienthoai")
    private List<Thuonghieudienthoai> thuonghieudienthoais;

    @OneToMany(mappedBy = "dienthoai")
    private List<Binhluandienthoai> binhluandienthoais;

    @OneToMany(mappedBy = "dienthoai")
    private List<Ketnoinhucau> ketnoinhucaus;

    @OneToMany(mappedBy = "dienthoai")
    private List<Yeuthich> yeuthichs;

    @OneToMany(mappedBy = "dienthoai")
    private List<Danhgia> danhgias;
}
