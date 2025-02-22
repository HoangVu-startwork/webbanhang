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
public class Thuonghieudienthoai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenthuonghieu;

    private String hinhanh;

    private String tinhtrang;

    private String dob;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;
}
