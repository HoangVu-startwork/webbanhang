package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Dienthoai;

@Repository
public interface DienthoaiRepository extends JpaRepository<Dienthoai, Long> {
    Dienthoai findByTensanpham(String tensanpham);

    @Query(
            value = "SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban "
                    + "FROM webbanhang.dienthoai dt "
                    + "JOIN ( "
                    + "    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban "
                    + "    FROM webbanhang.mausac m "
                    + "    JOIN ( "
                    + "        SELECT dienthoai_id, MIN(id) AS id "
                    + "        FROM webbanhang.mausac "
                    + "        GROUP BY dienthoai_id "
                    + "        ORDER BY RAND() "
                    + "    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id "
                    + ") ms ON dt.id = ms.dienthoai_id "
                    + "LIMIT 20",
            nativeQuery = true)
    List<Object[]> findPhoneProductsWithRandomColor1();

    @Query(
            value = "SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban "
                    + "FROM webbanhang.dienthoai dt "
                    + "JOIN ( "
                    + "    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban "
                    + "    FROM webbanhang.mausac m "
                    + "    JOIN ( "
                    + "        SELECT dienthoai_id, MIN(id) AS id "
                    + "        FROM webbanhang.mausac "
                    + "        GROUP BY dienthoai_id "
                    + "        ORDER BY RAND() "
                    + "    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id "
                    + ") ms ON dt.id = ms.dienthoai_id",
            nativeQuery = true)
    List<Object[]> findPhoneProductsWithRandomColor();

    @Query(
            value = "SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban "
                    + "FROM webbanhang.dienthoai dt "
                    + "JOIN ( "
                    + "    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban "
                    + "    FROM webbanhang.mausac m "
                    + "    JOIN ( "
                    + "        SELECT dienthoai_id, MIN(id) AS id "
                    + "        FROM webbanhang.mausac "
                    + "        GROUP BY dienthoai_id "
                    + "        ORDER BY RAND() "
                    + "    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id "
                    + ") ms ON dt.id = ms.dienthoai_id "
                    + "JOIN webbanhang.thongtinphanloai ttpl ON dt.thongtinphanloai_id = ttpl.id "
                    + "JOIN webbanhang.loaisanpham lsp ON ttpl.loaisanpham_id = lsp.id "
                    + "JOIN webbanhang.danhmuc dm ON lsp.danhmuc_id = dm.id "
                    + "JOIN webbanhang.hedieuhanh hd ON dm.hedieuhanh_id = hd.id "
                    + "WHERE (:ram IS NULL OR dt.ram IN :ram) "
                    + "AND (:hedieuhanh IS NULL OR hd.tenhedieuhanh = :hedieuhanh) "
                    + "AND (:boNho IS NULL OR dt.bonho = :boNho) "
                    + "AND (:giaTu IS NULL OR :giaDen IS NULL OR ms.giaban BETWEEN :giaTu AND :giaDen)",
            nativeQuery = true)
    List<Object[]> findPhoneProductsWithFilters(
            @Param("ram") List<String> ram,
            @Param("hedieuhanh") String hedieuhanh,
            @Param("boNho") String boNho,
            @Param("giaTu") Long giaTu,
            @Param("giaDen") Long giaDen);
}
