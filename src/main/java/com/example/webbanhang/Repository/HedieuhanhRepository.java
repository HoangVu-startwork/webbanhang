package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Hedieuhanh;

@Repository
public interface HedieuhanhRepository extends JpaRepository<Hedieuhanh, Long> {
    Hedieuhanh findByTenhedieuhanh(String tenhedieuhanh);
}
