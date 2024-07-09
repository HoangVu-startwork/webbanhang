package com.example.webbanhang.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Permission {
    @Id
    private String name;

    private String description;
}
