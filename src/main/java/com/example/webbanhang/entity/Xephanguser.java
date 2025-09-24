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
public class Xephanguser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hangmuc;
    private double giatien;

    @OneToMany(mappedBy = "xephanguser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Uudaimuahang> uudaimuahang;
}
