package com.example.webbanhang.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsById(String id);
}

// JpaRepository là một interface có sẵn trong Spring Data JPA cung cấp các phương thức tiêu chuẩn để thao tác với cơ sở
// dữ liệu.
// Nó được tham số hóa với hai kiểu: kiểu của entity (User trong trường hợp này) và kiểu của khóa chính của entity
// (String trong trường hợp này,
// do id của User được định nghĩa là kiểu String).

// Các phương thức mà UserRepository kế thừa từ JpaRepository bao gồm các phương thức để thêm, sửa, xóa, truy vấn và sắp
// xếp các bản ghi của User trong cơ sở dữ liệu.
