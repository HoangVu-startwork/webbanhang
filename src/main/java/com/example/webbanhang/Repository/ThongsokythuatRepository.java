package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Thongsokythuat;

@Repository
public interface ThongsokythuatRepository extends JpaRepository<Thongsokythuat, Long> {
    Thongsokythuat findByDienthoaiId(Long dienthoaiId);
}
