package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Chitiethoadon;

@Repository
public interface ChitiethoadonRepository extends JpaRepository<Chitiethoadon, Long> {}
