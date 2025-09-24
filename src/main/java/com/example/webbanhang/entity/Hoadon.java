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
public class Hoadon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mahd;

    private String dob;

    private String diachi;

    private double tongtien;

    private double giagiamhang;

    private String trangthai;

    private String ghichu;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String noidung;

    @Column(nullable = false, unique = true)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "hoadon")
    private List<Chitiethoadon> chitiethoadons;
}
