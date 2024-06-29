package com.example.webbanhang.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
//Entity là một annotation của JPA (Java Persistence API), được sử dụng để đánh dấu lớp User là một entity, tức là một đối tượng có thể được lưu trữ vào cơ sở dữ liệu.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class  User {
    //Id Đây là một annotation khác của JPA, được sử dụng để đánh dấu thuộc tính id là trường định danh (primary key) của entity
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Đây là một annotation của JPA được sử dụng để chỉ định cách sinh giá trị cho trường id. Trong trường hợp này,
    // GenerationType.UUID được sử dụng để chỉ định rằng giá trị của id sẽ được sinh ra bằng cách sử dụng chuỗi UUID (Universally Unique Identifier).
    // UUIDs là chuỗi 128-bit được thiết kế để đảm bảo duy nhất trên môi trường toàn cầu.
    private String id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String ngaysinh;
    private String firsName;
    private String lastName;
    private String dob;

    @ManyToMany
    Set<Role> roles;

    public void setDob(LocalDateTime dob) {
        this.dob = String.valueOf(dob);
    }


}
