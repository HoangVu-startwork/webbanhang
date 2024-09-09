package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Thongtinphanloai;

@Repository
public interface ThongtinphanloaiRepository extends JpaRepository<Thongtinphanloai, Long> {
    Thongtinphanloai findByTenphanloai(String tenphanloai);

    List<Thongtinphanloai> findByLoaisanphamId(Long loaisanphamId);
}
