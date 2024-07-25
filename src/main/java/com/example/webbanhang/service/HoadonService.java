package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.HoadonRequest;
import com.example.webbanhang.dto.response.HoadonResponse;
import com.example.webbanhang.entity.*;
import com.example.webbanhang.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HoadonService {
    private final UserRepository userRepository;
    private final GiohangRepository giohangRepository;
    private final KhodienthoaiRepository khodienthoaiRepository;
    private final HoadonRepository hoadonRepository;
    private final ChitiethoadonRepository chitiethoadonRepository;
    private final KhuyenmaiRepository khuyenmaiRepository;

    @Transactional
    public HoadonResponse createHoadon(HoadonRequest request) {
        // Lấy thông tin người dùng
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo mã hóa đơn mới
        String newMahd = generateNewMahd();

        // Tạo hóa đơn mới
        Hoadon hoadon = Hoadon.builder()
                .mahd(newMahd)
                .diachi(request.getDiachi())
                .tongtien(0.0) // Sẽ tính sau
                .user(user)
                .build();
        hoadonRepository.save(hoadon);

        double tongtien = 0.0;

        // Lấy các sản phẩm từ giỏ hàng
        List<Giohang> giohangList = giohangRepository.findByUser_Id(user.getId());

        for (Giohang giohang : giohangList) {
            // Kiểm tra số lượng tồn kho
            Khodienthoai khodienthoai = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
                    giohang.getDienthoai().getId(), giohang.getMausac().getId());
            if (khodienthoai == null) {
                throw new RuntimeException("Product or color not available in inventory");
            }

            int availableQuantity = 0;
            try {
                availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
            } catch (NumberFormatException e) {
                log.error(
                        "Invalid stock quantity for product: "
                                + giohang.getDienthoai().getTensanpham(),
                        e);
                throw new RuntimeException("Invalid stock quantity for product: "
                        + giohang.getDienthoai().getTensanpham());
            }

            int requestedQuantity = 0;
            try {
                requestedQuantity = Integer.parseInt(giohang.getSoluong());
            } catch (NumberFormatException e) {
                log.error(
                        "Invalid cart quantity for product: "
                                + giohang.getDienthoai().getTensanpham(),
                        e);
                throw new RuntimeException("Invalid cart quantity for product: "
                        + giohang.getDienthoai().getTensanpham());
            }

            if (availableQuantity < requestedQuantity) {
                throw new RuntimeException("Insufficient stock in inventory for product: "
                        + giohang.getDienthoai().getTensanpham());
            }

            // Lấy thông tin khuyến mãi nếu có
            double giamgia = 0.0;
            Khuyenmai khuyenmai = khuyenmaiRepository.findByDienthoai_Id(
                    giohang.getDienthoai().getId());
            if (khuyenmai != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime ngayBatDau = LocalDateTime.parse(khuyenmai.getNgaybatdau(), formatter);
                LocalDateTime ngayKetKhuc = LocalDateTime.parse(khuyenmai.getNgayketkhuc(), formatter);
                LocalDateTime today = LocalDateTime.now();

                if (!today.isBefore(ngayBatDau) && !today.isAfter(ngayKetKhuc)) {
                    try {
                        giamgia = Double.parseDouble(khuyenmai.getPhantramkhuyenmai());
                    } catch (NumberFormatException e) {
                        log.error(
                                "Invalid discount percentage for product: "
                                        + giohang.getDienthoai().getTensanpham(),
                                e);
                        throw new RuntimeException("Invalid discount percentage for product: "
                                + giohang.getDienthoai().getTensanpham());
                    }
                }
            }

            // Tính toán giá trị
            double giadienthoai = 0.0;
            try {
                giadienthoai = Double.parseDouble(giohang.getDienthoai().getGiaban());
            } catch (NumberFormatException e) {
                log.error("Invalid price for product: " + giohang.getDienthoai().getTensanpham(), e);
                throw new RuntimeException(
                        "Invalid price for product: " + giohang.getDienthoai().getTensanpham());
            }
            double thanhgia = giadienthoai * (1 - giamgia / 100);
            double giatong = thanhgia * requestedQuantity;

            // Tạo chi tiết hóa đơn
            Chitiethoadon chitiethoadon = Chitiethoadon.builder()
                    .dienthoai(giohang.getDienthoai())
                    .mausac(giohang.getMausac())
                    .soluong(giohang.getSoluong())
                    .giadienthoai(giadienthoai)
                    .thanhgia(thanhgia)
                    .giatong(giatong)
                    .giamgia(giamgia)
                    .hoadon(hoadon)
                    .build();
            chitiethoadonRepository.save(chitiethoadon);

            // Cập nhật số lượng tồn kho
            khodienthoai.setSoluong(String.valueOf(availableQuantity - requestedQuantity));
            khodienthoaiRepository.save(khodienthoai);

            // Tính tổng tiền
            tongtien += giatong;
        }

        // Cập nhật tổng tiền của hóa đơn
        hoadon.setTongtien(tongtien);
        hoadonRepository.save(hoadon);

        return HoadonResponse.builder()
                .id(hoadon.getId())
                .mahd(hoadon.getMahd())
                .diachi(hoadon.getDiachi())
                .tongtien(hoadon.getTongtien())
                .userId(user.getId())
                .build();
    }

    private String generateNewMahd() {
        String lastMahd =
                hoadonRepository.findTopByOrderByIdDesc().map(Hoadon::getMahd).orElse("MHD-00");
        int lastNumber = Integer.parseInt(lastMahd.split("-")[1]);
        return String.format("MHD-%02d", lastNumber + 1);
    }
}
