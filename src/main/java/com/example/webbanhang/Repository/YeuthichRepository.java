package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Yeuthich;

@Repository
public interface YeuthichRepository extends JpaRepository<Yeuthich, Long> {
    //    Yeuthich findByUserIdAndProductId(Long dienthoaiId, Long mausacId);
    Yeuthich findByUserIdAndDienthoaiIdAndMausacId(String userId, Long dienthoaiId, Long mausacId);

    List<Yeuthich> findAllByUserId(String userId);
}
