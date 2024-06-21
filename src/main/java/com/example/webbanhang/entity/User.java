package com.example.webbanhang.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//Entity là một annotation của JPA (Java Persistence API), được sử dụng để đánh dấu lớp User là một entity, tức là một đối tượng có thể được lưu trữ vào cơ sở dữ liệu.

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = String.valueOf(dob);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
}
