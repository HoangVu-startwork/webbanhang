package com.example.webbanhang.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Hoadon;

@Repository
public interface HoadonRepository extends JpaRepository<Hoadon, Long> {
    Optional<Hoadon> findTopByOrderByIdDesc();
}
