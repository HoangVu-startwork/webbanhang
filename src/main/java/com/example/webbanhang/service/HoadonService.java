package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.HoadonRequest;
import com.example.webbanhang.dto.response.HoadonResponse;
import com.example.webbanhang.entity.*;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.HoadonMapper;
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
    private final ThongsouserRepository thongsouserRepository;
    HoadonMapper hoadonMapper;

    //    @Transactional
    //    public HoadonResponse createHoadon(HoadonRequest request) {
    //        // Lấy thông tin người dùng
    //        User user = userRepository
    //                .findByEmail(request.getEmail())
    //                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    //
    //        // mã hóa đơn mới (mahd) được tạo ra bởi phương thức generateNewMahd. Mã này đảm bảo mỗi hóa đơn có một
    // định
    //        // danh duy nhất
    //        String newMahd = generateNewMahd();
    //
    //        // Tạo hóa đơn mới
    //        Hoadon hoadon = Hoadon.builder()
    //                .mahd(newMahd)
    //                .diachi(request.getDiachi())
    //                .tongtien(0.0) // Sẽ tính sau
    //                .user(user)
    //                .build();
    //        hoadonRepository.save(hoadon);
    //
    //        double tongtien = 0.0;
    //
    //        // lấy danh sách các sản phẩm trong giỏ hàng của người dùng
    //        List<Giohang> giohangList = giohangRepository.findByUser_Id(user.getId());
    //
    //        for (Giohang giohang : giohangList) {
    //            // kiểm tra xem có đủ tồn kho hay không. Nếu sản phẩm hoặc màu sắc không có sẵn, nó sẽ ném ra một
    // ngoại lệ.
    //            Khodienthoai khodienthoai = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
    //                    giohang.getDienthoai().getId(), giohang.getMausac().getId());
    //            if (khodienthoai == null) {
    //                throw new AppException(ErrorCode.SANPHAMHOADON);
    //            }
    //
    //            // kiểm tra số lượng có sẵn và số lượng yêu cầu. Nếu số lượng có sẵn ít hơn số lượng yêu cầu, nó sẽ
    // ném ra
    //            // một ngoại lệ
    //            int availableQuantity = 0;
    //            try {
    //                availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
    //            } catch (NumberFormatException e) {
    //                throw new AppException(ErrorCode.SANPHAMHOADON);
    //            }
    //
    //            int requestedQuantity = 0;
    //            try {
    //                requestedQuantity = Integer.parseInt(giohang.getSoluong());
    //            } catch (NumberFormatException e) {
    //                throw new AppException(ErrorCode.SANPHAMHOADON);
    //            }
    //
    //            if (availableQuantity < requestedQuantity) {
    //                throw new AppException(ErrorCode.SANPHAMHOADON);
    //            }
    //
    //            // Lấy thông tin khuyến mãi nếu có
    //            double giamgia = 0.0;
    //            Khuyenmai khuyenmai = khuyenmaiRepository.findByDienthoai_Id(
    //                    giohang.getDienthoai().getId());
    //            if (khuyenmai != null) {
    //                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    //                LocalDateTime ngayBatDau = LocalDateTime.parse(khuyenmai.getNgaybatdau(), formatter);
    //                LocalDateTime ngayKetKhuc = LocalDateTime.parse(khuyenmai.getNgayketkhuc(), formatter);
    //                LocalDateTime today = LocalDateTime.now();
    //
    //                if (!today.isBefore(ngayBatDau) && !today.isAfter(ngayKetKhuc)) {
    //                    try {
    //                        giamgia = Double.parseDouble(khuyenmai.getPhantramkhuyenmai());
    //                    } catch (NumberFormatException e) {
    //                        throw new RuntimeException("Invalid discount percentage for product: "
    //                                + giohang.getDienthoai().getTensanpham());
    //                    }
    //                }
    //            }
    //
    //            // Tính toán giá trị
    //            double giadienthoai = 0.0;
    //            try {
    //                giadienthoai = Double.parseDouble(giohang.getDienthoai().getGiaban());
    //            } catch (NumberFormatException e) {
    //                throw new RuntimeException(
    //                        "Invalid price for product: " + giohang.getDienthoai().getTensanpham());
    //            }
    //            double thanhgia = giadienthoai * (1 - giamgia / 100);
    //            double giatong = thanhgia * requestedQuantity;
    //
    //            // Tạo chi tiết hóa đơn
    //            Chitiethoadon chitiethoadon = Chitiethoadon.builder()
    //                    .dienthoai(giohang.getDienthoai())
    //                    .mausac(giohang.getMausac())
    //                    .soluong(giohang.getSoluong())
    //                    .giadienthoai(giadienthoai)
    //                    .thanhgia(thanhgia)
    //                    .giatong(giatong)
    //                    .giamgia(giamgia)
    //                    .hoadon(hoadon)
    //                    .build();
    //            chitiethoadonRepository.save(chitiethoadon);
    //
    //            // Cập nhật số lượng tồn kho
    //            khodienthoai.setSoluong(String.valueOf(availableQuantity - requestedQuantity));
    //            khodienthoaiRepository.save(khodienthoai);
    //
    //            // Tính tổng tiền
    //            tongtien += giatong;
    //        }
    //
    //        // Cập nhật tổng tiền của hóa đơn
    //        hoadon.setTongtien(tongtien);
    //        hoadonRepository.save(hoadon);
    //
    //        return HoadonResponse.builder()
    //                .id(hoadon.getId())
    //                .mahd(hoadon.getMahd())
    //                .diachi(hoadon.getDiachi())
    //                .tongtien(hoadon.getTongtien())
    //                .userId(user.getId())
    //                .build();
    //    }

    private String generateNewMahd() {
        String lastMahd =
                hoadonRepository.findTopByOrderByIdDesc().map(Hoadon::getMahd).orElse("MHD-00");
        int lastNumber = Integer.parseInt(lastMahd.split("-")[1]);
        return String.format("MHD-%02d", lastNumber + 1);
    }

    public HoadonResponse getHoadonById(Long id) {
        Hoadon hoadon = hoadonRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.HOADON));
        return HoadonResponse.builder()
                .id(hoadon.getId())
                .mahd(hoadon.getMahd())
                .diachi(hoadon.getDiachi())
                .tongtien(hoadon.getTongtien())
                .userId(hoadon.getUser().getId())
                .build();
    }

    public List<HoadonResponse> getHoadonByUserId(String userId) {
        List<Hoadon> hoadons = hoadonRepository.findByUserId(userId);
        return hoadons.stream().map(hoadonMapper::toHoadonResponse).toList();
    }

    //    public List<HoadonResponse> getHoadonByUserId(String userId) {
    //        List<Hoadon> hoadons = hoadonRepository.findByUserId(userId);
    //        return hoadons.stream().map(hoadonMapper::toHoadonResponse).collect(Collectors.toList());
    //    }

    @Transactional
    public HoadonResponse createHoadon(HoadonRequest request) {
        // Lấy thông tin người dùng
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Mã hóa đơn mới (mahd) được tạo ra bởi phương thức generateNewMahd
        String newMahd = generateNewMahd();
        DateTimeFormatter formattert = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateT = LocalDateTime.now();
        String formattedDate = currentDateT.format(formattert);
        // Tạo và lưu hóa đơn mới vào cơ sở dữ liệu
        Hoadon hoadon = Hoadon.builder()
                .mahd(newMahd)
                .diachi(request.getDiachi())
                .tongtien(0.0) // Sẽ tính sau
                .dob(formattedDate)
                .user(user)
                .build();
        hoadonRepository.save(hoadon);

        double tongtien = 0.0;

        // Lấy danh sách các sản phẩm theo ID trong giỏ hàng của người dùng
        List<Giohang> giohangList = request.getProductIds().stream()
                .map(productId -> giohangRepository.findById(productId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        for (Giohang giohang : giohangList) {
            // Kiểm tra tồn kho và xử lý thanh toán
            Khodienthoai khodienthoai = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
                    giohang.getDienthoai().getId(), giohang.getMausac().getId());
            if (khodienthoai == null) {
                throw new AppException(ErrorCode.SANPHAMHOADON);
            }

            int availableQuantity;
            try {
                availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
            } catch (NumberFormatException e) {
                throw new AppException(ErrorCode.SANPHAMHOADON);
            }

            int requestedQuantity;
            try {
                requestedQuantity = Integer.parseInt(giohang.getSoluong());
            } catch (NumberFormatException e) {
                throw new AppException(ErrorCode.SANPHAMHOADON);
            }

            if (availableQuantity < requestedQuantity) {
                throw new AppException(ErrorCode.SANPHAMHOADON);
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
                        throw new RuntimeException("Invalid discount percentage for product: "
                                + giohang.getDienthoai().getTensanpham());
                    }
                }
            }

            // Tính toán giá trị
            double giadienthoai;
            try {
                giadienthoai = Double.parseDouble(giohang.getDienthoai().getGiaban());
            } catch (NumberFormatException e) {
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
                .dob(hoadon.getDob())
                .tongtien(hoadon.getTongtien())
                .userId(user.getId())
                .build();
    }

    //
    @Transactional
    public HoadonResponse createHoadon1(HoadonRequest request) {
        // Lấy thông tin người dùng
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Mã hóa đơn mới (mahd) được tạo ra bởi phương thức generateNewMahd
        String newMahd = generateNewMahd();
        DateTimeFormatter formattert = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateT = LocalDateTime.now();
        String formattedDate = currentDateT.format(formattert);

        // Tạo và lưu hóa đơn mới vào cơ sở dữ liệu
        Hoadon hoadon = Hoadon.builder()
                .mahd(newMahd)
                .diachi(request.getDiachi())
                .tongtien(0.0) // Sẽ tính sau
                .dob(formattedDate)
                .trangthai("Không thành công") // Đặt trạng thái ban đầu là không thành công
                .user(user)
                .build();
        hoadonRepository.save(hoadon);

        double tongtien = 0.0;

        // Lấy danh sách các sản phẩm theo ID trong giỏ hàng của người dùng
        List<Giohang> giohangList = request.getProductIds().stream()
                .map(productId -> giohangRepository.findById(productId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        try {
            for (Giohang giohang : giohangList) {
                // Kiểm tra tồn kho và xử lý thanh toán
                Khodienthoai khodienthoai = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
                        giohang.getDienthoai().getId(), giohang.getMausac().getId());
                if (khodienthoai == null) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
                }

                int availableQuantity;
                try {
                    availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
                } catch (NumberFormatException e) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
                }

                int requestedQuantity;
                try {
                    requestedQuantity = Integer.parseInt(giohang.getSoluong());
                } catch (NumberFormatException e) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
                }

                if (availableQuantity < requestedQuantity) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
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
                            throw new RuntimeException("Invalid discount percentage for product: "
                                    + giohang.getDienthoai().getTensanpham());
                        }
                    }
                }

                // Tính toán giá trị
                double giadienthoai;
                try {
                    giadienthoai = Double.parseDouble(giohang.getDienthoai().getGiaban());
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid price for product: "
                            + giohang.getDienthoai().getTensanpham());
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

            // Cập nhật tổng tiền và trạng thái của hóa đơn
            hoadon.setTongtien(tongtien);
            hoadon.setTrangthai("Thanh toán thành công"); // Cập nhật trạng thái thành công
            hoadonRepository.save(hoadon);

            // Kiểm tra và cập nhật thông tin vào bảng Thongsouser
            Thongsouser thongsouser =
                    thongsouserRepository.findByUserId(user.getId()).orElse(null);
            if (thongsouser == null) {
                thongsouser = Thongsouser.builder().user(user).tien(tongtien).build();
            } else {
                thongsouser.setTien(thongsouser.getTien() + tongtien);
            }
            thongsouserRepository.save(thongsouser);
            thongsouserRepository.save(thongsouser);

        } catch (AppException e) {
            // Cập nhật trạng thái không thành công nếu có lỗi xảy ra
            hoadon.setTrangthai("Không thành công");
            hoadonRepository.save(hoadon);
            throw e;
        }

        return HoadonResponse.builder()
                .id(hoadon.getId())
                .mahd(hoadon.getMahd())
                .diachi(hoadon.getDiachi())
                .dob(hoadon.getDob())
                .tongtien(hoadon.getTongtien())
                .userId(user.getId())
                .trangthai(hoadon.getTrangthai()) // Trả về trạng thái
                .build();
    }
}
