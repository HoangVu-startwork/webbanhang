package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Nhucaudienthoai;

@Repository
public interface NhucaudienthoaiRepository extends JpaRepository<Nhucaudienthoai, Long> {
    boolean existsByTennhucau(String tennhucau);
}
