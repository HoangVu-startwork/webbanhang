package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Khodienthoai;

@Repository
public interface KhodienthoaiRepository extends JpaRepository<Khodienthoai, Long> {
    Khodienthoai findByDienthoaiIdAndMausacId(Long dienthoaiId, Long mausacId);
}
