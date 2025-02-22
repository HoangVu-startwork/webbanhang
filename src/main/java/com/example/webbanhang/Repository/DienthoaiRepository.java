package com.example.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thongtinphanloai;

@Repository
public interface DienthoaiRepository extends JpaRepository<Dienthoai, Long> {
    Dienthoai findByTensanpham(String tensanpham);

    Dienthoai findByid(Long dienthoaiId);

    @Query("SELECT d.thongtinphanloai FROM Dienthoai d WHERE d.id = :dienthoaiId")
    Thongtinphanloai findThongtinphanloaiByDienthoaiId(@Param("dienthoaiId") Long dienthoaiId);

    boolean existsById(Long id);

    @Query(
            "SELECT dt FROM Dienthoai dt JOIN dt.thongtinphanloai tp JOIN tp.loaisanpham ls JOIN ls.danhmuc dm JOIN dm.hedieuhanh hd WHERE hd.id = :hedieuhanhId")
    List<Dienthoai> findByHedieuhanhId(@Param("hedieuhanhId") Long hedieuhanhId);

    @Query(
            "SELECT dt FROM Dienthoai dt JOIN dt.thongtinphanloai tp JOIN tp.loaisanpham ls WHERE ls.id = :loaisanphamId")
    List<Dienthoai> findByLoaisanphamId(@Param("loaisanphamId") Long loaisanphamId);

    @Query(
            "SELECT dt FROM Dienthoai dt JOIN dt.thongtinphanloai tp JOIN tp.loaisanpham ls JOIN ls.danhmuc dm WHERE dm.id = :danhmucId")
    List<Dienthoai> findByDanhmucId(@Param("danhmucId") Long danhmucId);

    @Query("SELECT dt FROM Dienthoai dt JOIN dt.thongtinphanloai tp WHERE tp.id = :thongtinphanloaiId")
    List<Dienthoai> findByThongtinphanloaiId(@Param("thongtinphanloaiId") Long thongtinphanloaiId);

    @Query(
            value = "SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban, "
                    + "CASE "
                    + "    WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.phantramkhuyenmai "
                    + "    ELSE NULL "
                    + "END AS phantramkhuyenmai, "
                    + "CASE "
                    + "    WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.noidungkhuyenmai "
                    + "    ELSE NULL "
                    + "END AS noidungkhuyenmai, "
                    + "CASE "
                    + "    WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.ngaybatdau "
                    + "    ELSE NULL "
                    + "END AS ngaybatdau, "
                    + "CASE "
                    + "    WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.ngayketkhuc "
                    + "    ELSE NULL "
                    + "END AS ngayketkhuc, "
                    + "tt.tinhtrangmay, tt.thietbidikem, tt.baohanh, "
                    + "ms.id AS mausac_id, "
                    + "tt.id AS thongtindienthoai_id, "
                    + "km.id AS khuyenmai_id "
                    + "FROM dienthoai dt "
                    + "JOIN ( "
                    + "    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban, m.id "
                    + "    FROM mausac m "
                    + "    JOIN ( "
                    + "        SELECT dienthoai_id, MIN(id) AS id "
                    + "        FROM mausac "
                    + "        GROUP BY dienthoai_id "
                    + "        ORDER BY RAND() "
                    + "    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id "
                    + ") ms ON dt.id = ms.dienthoai_id "
                    + "LEFT JOIN khuyenmai km ON dt.id = km.dienthoai_id "
                    + "AND CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc "
                    + "LEFT JOIN thongtindienthoai tt ON dt.id = tt.dienthoai_id "
                    + "WHERE dt.tinhtrang = 'Mở' "
                    + "LIMIT 20",
            nativeQuery = true)
    List<Object[]> findPhoneProductsWithRandomColor1();

    @Query(
            value = "SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban "
                    + "FROM dienthoai dt "
                    + "JOIN ( "
                    + "    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban "
                    + "    FROM mausac m "
                    + "    JOIN ( "
                    + "        SELECT dienthoai_id, MIN(id) AS id "
                    + "        FROM mausac "
                    + "        GROUP BY dienthoai_id "
                    + "        ORDER BY RAND() "
                    + "    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id "
                    + ") ms ON dt.id = ms.dienthoai_id",
            nativeQuery = true)
    List<Object[]> findPhoneProductsWithRandomColor();

    @Query(
            value = "SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban "
                    + "FROM dienthoai dt "
                    + "JOIN ( "
                    + "    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban "
                    + "    FROM mausac m "
                    + "    JOIN ( "
                    + "        SELECT dienthoai_id, MIN(id) AS id "
                    + "        FROM mausac "
                    + "        GROUP BY dienthoai_id "
                    + "        ORDER BY RAND() "
                    + "    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id "
                    + ") ms ON dt.id = ms.dienthoai_id "
                    + "JOIN thongtinphanloai ttpl ON dt.thongtinphanloai_id = ttpl.id "
                    + "JOIN loaisanpham lsp ON ttpl.loaisanpham_id = lsp.id "
                    + "JOIN danhmuc dm ON lsp.danhmuc_id = dm.id "
                    + "JOIN hedieuhanh hd ON dm.hedieuhanh_id = hd.id "
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

    @Query(
            value =
                    "SELECT dt.id, dt.tensanpham, dt.hinhanh, dt.hinhanhduyet, dt.ram, dt.bonho, dt.giaban, dt.tinhtrang,"
                            + "GROUP_CONCAT(ms.tenmausac) AS tenmausacs, GROUP_CONCAT(ms.id) AS idmausacs, GROUP_CONCAT(ms.hinhanh) AS hinhanhs, GROUP_CONCAT(ms.giaban) AS giabans "
                            + "FROM dienthoai dt "
                            + "LEFT JOIN mausac ms ON dt.id = ms.dienthoai_id "
                            + "GROUP BY dt.id, dt.tensanpham, dt.hinhanh, dt.hinhanhduyet, dt.ram, dt.bonho, dt.giaban, dt.tinhtrang",
            nativeQuery = true)
    List<Object[]> findPhoneProductsWithAllColors();

    // JOIN mặc định là INNER JOIN. Điều này có nghĩa là nếu điện thoại không có màu sắc (không có bản ghi tương ứng
    // trong bảng mausac), thì kết quả sẽ không trả về thông tin của điện thoại đó.
    // LEFT JOIN: Giúp lấy tất cả các bản ghi từ bảng dienthoai, dù có dữ liệu tương ứng trong bảng mausac hay không.
    // Nếu không có dữ liệu tương ứng, các cột từ bảng mausac sẽ trả về NULL.
    // GROUP_CONCAT: Kết hợp các giá trị màu sắc, id màu sắc, hình ảnh, và giá bán từ bảng mausac nếu tồn tại. Nếu không
    // có màu sắc nào, các trường này sẽ là NULL hoặc chuỗi rỗng.

}
