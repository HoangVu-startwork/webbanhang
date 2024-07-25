package com.example.webbanhang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Giohang;

@Repository
public interface GiohangRepository extends JpaRepository<Giohang, Long> {

    Optional<Giohang> findById(Long id);

    List<Giohang> findByUser_Id(String userId);

    // Delete cart item by its ID
    void deleteById(Long id);

    Giohang findByUserIdAndDienthoaiIdAndMausacId(String userId, Long dienthoaiId, Long mausacId);

    @Query(
            "SELECT g FROM Giohang g WHERE g.user.id = :userId AND g.dienthoai.id = :dienthoaiId AND g.mausac.id = :mausacId")
    Optional<Giohang> findByUserId_AndDienthoaiId_And_MausacId(
            @Param("userId") String userId, @Param("dienthoaiId") Long dienthoaiId, @Param("mausacId") Long mausacId);
}
