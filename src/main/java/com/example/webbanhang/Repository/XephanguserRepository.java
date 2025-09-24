package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Xephanguser;

@Repository
public interface XephanguserRepository extends JpaRepository<Xephanguser, Long> {
    List<Xephanguser> findAll();
}
