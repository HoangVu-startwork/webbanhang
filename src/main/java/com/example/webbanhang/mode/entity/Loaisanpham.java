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
