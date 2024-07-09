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
public class Danhmuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tendanhmuc;

    private String hinhanh;

    @ManyToOne
    @JoinColumn(name = "mucluc_id")
    private Mucluc mucluc;

    @ManyToOne
    @JoinColumn(name = "hedieuhanh_id")
    private Hedieuhanh hedieuhanh;
}
