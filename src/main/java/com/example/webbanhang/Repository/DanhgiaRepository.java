package com.example.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Danhgia;

@Repository
public interface DanhgiaRepository extends JpaRepository<Danhgia, Long> {
    Danhgia findByDienthoaiId(Long dienthoaiId);

    @Query("SELECT AVG(danhgia.diem) FROM Danhgia danhgia WHERE danhgia.dienthoai.id = :dienthoaiId")
    Double findTongsao(@Param("dienthoaiId") Long dienthoaiId);

    @Query(
            "SELECT COUNT(danhgia.id) FROM Danhgia danhgia WHERE danhgia.dienthoai.id = :dienthoaiId AND danhgia.diem = 5")
    Long count5sao(@Param("dienthoaiId") Long dienthoaiId);

    @Query(
            "SELECT COUNT(danhgia.id) FROM Danhgia danhgia WHERE danhgia.dienthoai.id = :dienthoaiId AND danhgia.diem = 4")
    Long count4sao(@Param("dienthoaiId") Long dienthoaiId);

    @Query(
            "SELECT COUNT(danhgia.id) FROM Danhgia danhgia WHERE danhgia.dienthoai.id = :dienthoaiId AND danhgia.diem = 3")
    Long count3sao(@Param("dienthoaiId") Long dienthoaiId);

    @Query(
            "SELECT COUNT(danhgia.id) FROM Danhgia danhgia WHERE danhgia.dienthoai.id = :dienthoaiId AND danhgia.diem = 2")
    Long count2sao(@Param("dienthoaiId") Long dienthoaiId);

    @Query(
            "SELECT COUNT(danhgia.id) FROM Danhgia danhgia WHERE danhgia.dienthoai.id = :dienthoaiId AND danhgia.diem = 1")
    Long count1sao(@Param("dienthoaiId") Long dienthoaiId);
}
