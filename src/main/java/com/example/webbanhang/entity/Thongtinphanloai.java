package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Thongtinphanloai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenphanloai;

    @ManyToOne
    @JoinColumn(name = "loaisanpham_id")
    private Loaisanpham loaisanpham;
}
