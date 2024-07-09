package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Slf4j
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
