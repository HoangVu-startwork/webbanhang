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
public class Giohang {

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
