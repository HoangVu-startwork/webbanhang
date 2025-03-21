package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Thuonghieudienthoai;

@Repository
public interface ThuonghieudienthoaiRepository extends JpaRepository<Thuonghieudienthoai, Long> {
    List<Thuonghieudienthoai> findByTinhtrang(String tinhtrang);
}
