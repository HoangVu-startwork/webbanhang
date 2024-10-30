package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Khuyenmai;

@Repository
public interface KhuyenmaiRepository extends JpaRepository<Khuyenmai, Long> {
    Khuyenmai findByDienthoai_IdAndNgaybatdauBeforeAndNgayketkhucAfter(
            Long dienthoaiId, String ngaybatdau, String ngayketkhuc);

    Khuyenmai findByDienthoai_Id(Long dienthoaiId);

    List<Khuyenmai> findByDienthoaiId(Long dienthoaiId);

    List<Khuyenmai> findByDienthoai(Dienthoai dienthoai);
}
