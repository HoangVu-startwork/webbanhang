package com.example.webbanhang.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
public class User {
    // Id Đây là một annotation khác của JPA, được sử dụng để đánh dấu thuộc tính id là trường định danh (primary key)
    // của entity
    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.UUID) // Đây là một annotation của JPA được sử dụng để chỉ định cách sinh giá trị cho
    // trường id. Trong trường hợp này,
    // GenerationType.UUID được sử dụng để chỉ định rằng giá trị của id sẽ được sinh ra bằng cách sử dụng chuỗi UUID
    // (Universally Unique Identifier).
    // UUIDs là chuỗi 128-bit được thiết kế để đảm bảo duy nhất trên môi trường toàn cầu.
    private String id;

    private String username;
    private String password;

    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String email;

    private String phone;
    private String ngaysinh;
    private String firsName;
    private String lastName;
    private String dob;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    Set<Role> roles;

    public void setDob(LocalDateTime dob) {
        this.dob = String.valueOf(dob);
    }

    @OneToMany(mappedBy = "user")
    private List<Giohang> giohangs;

    @OneToMany(mappedBy = "user")
    private List<Nhapkho> nhapkhos;

    @OneToMany(mappedBy = "user")
    private List<Hoadon> Hoadons;

    @OneToMany(mappedBy = "user")
    private List<Binhluandienthoai> binhluandienthoais;

    @OneToMany(mappedBy = "user")
    private List<Yeuthich> yeuthichs;

    @OneToMany(mappedBy = "user")
    private List<Danhgia> danhgias;

    @OneToMany(mappedBy = "user")
    private List<Thongsouser> thongsousers;
}
