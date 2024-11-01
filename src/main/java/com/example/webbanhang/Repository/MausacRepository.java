package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Mausac;

@Repository
public interface MausacRepository extends JpaRepository<Mausac, Long> {
    Mausac findByDienthoaiIdAndTenmausac(Long dienthoaiId, String tenmausac);

    //    List<Mausac> findByDienthoaiId(Long dienthoaiId, Long id);
}
