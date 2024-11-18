package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Thuonghieumenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hinhanh;
    private String label;
    private String text;
    private String tinhtrang;
    private String dob;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;
}
