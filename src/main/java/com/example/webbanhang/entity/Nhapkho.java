package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Nhapkho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;

    @ManyToOne
    @JoinColumn(name = "mausac_id")
    private Mausac mausac;

    private String soluong;

    private String dob;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
