package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Mausac;

@Repository
public interface MausacRepository extends JpaRepository<Mausac, Long> {
    Mausac findByDienthoaiIdAndTenmausac(Long dienthoaiId, String tenmausac);

    //    Mausac findByDienthoaiIdAndMausacId(Long dienthoaiId, Long mausacId);

    Mausac findByDienthoai_IdAndId(Long dienthoaiId, Long mausacId);

    boolean existsByDienthoai_IdAndId(Long dienthoaiId, Long mausacId);

    List<Mausac> findByDienthoaiId(Long dienthoaiId);

    @Modifying
    @Query("DELETE FROM Mausac m WHERE m.dienthoai.id = :dienthoaiId")
    int deleteByDienthoai_Id(Long dienthoaiId);

    //    List<Mausac> findByDienthoaiId(Long dienthoaiId, Long id);
}
