package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Nhapkho;

@Repository
public interface NhapkhoRepository extends JpaRepository<Nhapkho, Long> {
    Nhapkho findByid(Long id);
}
