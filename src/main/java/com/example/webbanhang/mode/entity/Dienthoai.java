package com.example.webbanhang.entity;

import java.util.Arrays;
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

    @ManyToOne
    @JoinColumn(name = "thongtinphanloai_id")
    private Thongtinphanloai thongtinphanloai;

    @OneToMany(mappedBy = "dienthoai")
    private List<Khuyenmai> khuyenmais;

    @OneToMany(mappedBy = "dienthoai")
    private List<Mausac> mausacs;

    @OneToMany(mappedBy = "dienthoai")
    private List<Thongtindienthoai> thongtindienthoais;

    @OneToMany(mappedBy = "dienthoai")
    private List<Noibat> noibats;

    @OneToMany(mappedBy = "dienthoai")
    private List<Thongsokythuat> thongsokythuats;
}
