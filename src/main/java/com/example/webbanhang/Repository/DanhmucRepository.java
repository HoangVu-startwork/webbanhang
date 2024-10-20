package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Danhmuc;

@Repository
public interface DanhmucRepository extends JpaRepository<Danhmuc, Long> {
    Danhmuc findBytendanhmuc(String tendanhmuc);

    List<Danhmuc> findByMuclucId(Long muclucId);

    void deleteById(Long id);
}
