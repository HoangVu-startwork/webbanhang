package com.example.webbanhang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Khodienthoai;

@Repository
public interface KhodienthoaiRepository extends JpaRepository<Khodienthoai, Long> {
    Khodienthoai findByDienthoaiIdAndMausacId(Long dienthoaiId, Long mausacId);

    @Query(
            "SELECT k FROM Khodienthoai k WHERE k.dienthoai.id = :dienthoaiId AND k.mausac.id = :mausacId ORDER BY k.id DESC")
    Optional<Khodienthoai> findLatestByDienthoaiIdAndMausacId(
            @Param("dienthoaiId") Long dienthoaiId, @Param("mausacId") Long mausacId);

    @Query(
            "SELECT k FROM Khodienthoai k WHERE k.dienthoai.id = :dienthoaiId AND k.mausac.id = :mausacId ORDER BY k.id DESC")
    List<Khodienthoai> findLatestByDienthoaiIdMausacId(
            @Param("dienthoaiId") Long dienthoaiId, @Param("mausacId") Long mausacId);
}
