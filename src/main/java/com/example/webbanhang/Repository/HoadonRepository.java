package com.example.webbanhang.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.webbanhang.dto.response.DailySummaryResponse;
import com.example.webbanhang.entity.Hoadon;

@Repository
public interface HoadonRepository extends JpaRepository<Hoadon, Long> {
    Optional<Hoadon> findTopByOrderByIdDesc();

    List<Hoadon> findByUserId(String userId);

    Optional<Hoadon> findFirstByTransactionId(String transactionId);

    Optional<Hoadon> findByMahd(String mahd);

    // JPQL COALESCE(SUM(...),0) tránh trả null nếu không có hoá đơn trong ngày.
    @Query(
            "SELECT new com.example.webbanhang.dto.response.DailySummaryResponse(:date, COUNT(h), COALESCE(SUM(h.tongtien), 0)) "
                    + "FROM Hoadon h WHERE h.dob = :date")
    DailySummaryResponse findDailySummaryByDob(@Param("date") String date);

    @Query(
            "SELECT new com.example.webbanhang.dto.response.DailySummaryResponse(h.dob, COUNT(h), COALESCE(SUM(h.tongtien),0)) "
                    + "FROM Hoadon h "
                    + "GROUP BY h.dob "
                    + "ORDER BY h.dob DESC")
    List<DailySummaryResponse> findAllDailySummaries();

    // 1) Lấy list tổng theo ngày trong tháng (date string yyyy-MM-dd, ordered ascending)
    @Query(
            "SELECT new com.example.webbanhang.dto.response.DailySummaryResponse(h.dob, COUNT(h), COALESCE(SUM(h.tongtien), 0)) "
                    + "FROM Hoadon h "
                    + "WHERE SUBSTRING(h.dob, 1, 7) = :yearMonth "
                    + "GROUP BY h.dob "
                    + "ORDER BY h.dob ASC")
    List<DailySummaryResponse> findDailySummariesByYearMonth(@Param("yearMonth") String yearMonth);

    // 2) Lấy tổng toàn tháng (count, sum) — trả về Object[] {Long count, Double sum}
    @Query("SELECT COUNT(h), COALESCE(SUM(h.tongtien), 0) FROM Hoadon h WHERE SUBSTRING(h.dob, 1, 7) = :yearMonth")
    Object[] findMonthlyTotalsByYearMonth(@Param("yearMonth") String yearMonth);

    @Query("SELECT COUNT(h), COALESCE(SUM(h.tongtien), 0) FROM Hoadon h")
    Object[] getTotalOrdersAndAndAmount();

    @Query("SELECT COUNT(h), COALESCE(SUM(h.tongtien),0) FROM Hoadon h WHERE h.user.id = :userId")
    Object[] getTotalOrdersAndAmountByUser(@Param("userId") String userId);
}
