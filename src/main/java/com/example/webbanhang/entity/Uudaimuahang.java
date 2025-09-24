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
public class Uudaimuahang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String noidunguudai;
    private String dieukienuudai;
    private String phantramkhuyenmai;
    private String giakhuyenmai;
    private String dieukienthucthi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xephanguser_id")
    private Xephanguser xephanguser;
}
