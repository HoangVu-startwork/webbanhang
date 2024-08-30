package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Ketnoinhucau;

@Repository
public interface KetnoinhucauRepository extends JpaRepository<Ketnoinhucau, Long> {
    boolean existsByDienthoaiIdAndNhucaudienthoaiId(Long dienthoaiId, Long nhucaudienthoaiId);
}
