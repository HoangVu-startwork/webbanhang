package com.example.webbanhang.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Role {
    @Id
    private String name;

    private String description;

    @ManyToMany
    Set<Permission> permissions;

    @ManyToMany(mappedBy = "roles")
    Set<User> users;
}
