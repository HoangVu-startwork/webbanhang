package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Binhluandienthoai;

@Repository
public interface BinhluandienthoaiRepository extends JpaRepository<Binhluandienthoai, Long> {
    List<Binhluandienthoai> findByDienthoaiIdAndParentCommentIsNull(Long dienthoaiId);

    List<Binhluandienthoai> findByParentCommentId(Long parentCommentId);
}
