package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Mucluc;

@Repository
public interface MuclucRepository extends JpaRepository<Mucluc, String> {
    Mucluc findByTenmucluc(String tenmucluc);
}