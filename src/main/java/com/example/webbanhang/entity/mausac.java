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
}
