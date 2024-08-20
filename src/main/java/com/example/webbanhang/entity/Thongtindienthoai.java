package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Thongtindienthoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tinhtrangmay;
    private String thietbidikem;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String baohanh;

    @ManyToOne
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;
}
