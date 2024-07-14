package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Dienthoai;

@Repository
public interface DienthoaiRepository extends JpaRepository<Dienthoai, Long> {
    Dienthoai findByTensanpham(String tensanpham);
}
