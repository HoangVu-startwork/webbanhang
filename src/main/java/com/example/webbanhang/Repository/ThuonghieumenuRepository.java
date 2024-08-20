package com.example.webbanhang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Thuonghieumenu;

@Repository
public interface ThuonghieumenuRepository extends JpaRepository<Thuonghieumenu, Long> {
    Page<Thuonghieumenu> findAll(Pageable pageable);

    Page<Thuonghieumenu> findByTinhtrang(String tinhtrang, Pageable pageable);
}
