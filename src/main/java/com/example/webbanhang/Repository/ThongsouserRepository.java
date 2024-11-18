package com.example.webbanhang.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Thongsouser;

@Repository
public interface ThongsouserRepository extends JpaRepository<Thongsouser, Long> {
    Optional<Thongsouser> findByUserId(String userId);
}
