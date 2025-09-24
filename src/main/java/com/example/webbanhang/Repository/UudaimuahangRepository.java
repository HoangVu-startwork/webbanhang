package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Uudaimuahang;
import com.example.webbanhang.entity.Xephanguser;

@Repository
public interface UudaimuahangRepository extends JpaRepository<Uudaimuahang, Long> {
    List<Uudaimuahang> findByXephanguser(Xephanguser xephanguser);

    List<Uudaimuahang> findAllByXephanguser_Id(Long xephanguserId);
}
