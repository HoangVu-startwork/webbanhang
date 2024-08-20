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
public class Loaisanpham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenloaisanpham;

    @ManyToOne
    @JoinColumn(name = "danhmuc_id")
    private Danhmuc danhmuc;

    @OneToMany(mappedBy = "loaisanpham")
    private List<Thongtinphanloai> thongtinphanloais;
}
