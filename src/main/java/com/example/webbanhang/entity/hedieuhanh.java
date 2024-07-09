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
public class Hedieuhanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenhedieuhanh;

    @OneToMany(mappedBy = "hedieuhanh")
    private List<Danhmuc> danhmucs;
}
