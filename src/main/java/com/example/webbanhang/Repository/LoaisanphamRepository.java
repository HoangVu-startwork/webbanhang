package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Loaisanpham;

@Repository
public interface LoaisanphamRepository extends JpaRepository<Loaisanpham, Long> {
    Loaisanpham findByTenloaisanpham(String tenloaisanpham);

    List<Loaisanpham> findByDanhmucId(Long danhmucId);

    void deleteById(Long id);
}
