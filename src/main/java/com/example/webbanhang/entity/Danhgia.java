package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Slf4j
public class Danhgia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String chitiet;

    @Column(nullable = false)
    private double diem;

    private String dob;

    @ManyToOne
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
