package com.example.webbanhang.service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.HoadonRequest;
import com.example.webbanhang.dto.response.DailySummaryResponse;
import com.example.webbanhang.dto.response.HoadonResponse;
import com.example.webbanhang.dto.response.HoadonSummaryResponse;
import com.example.webbanhang.dto.response.MonthlySummaryResponse;
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
    private final XephanguserRepository xephanguserRepository;
    private final UudaimuahangRepository uudaimuahangRepository;
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
                .ghichu(hoadon.getGhichu())
                .tongtien(hoadon.getTongtien())
                .userId(hoadon.getUser().getId())
                .build();
    }

    public List<HoadonResponse> getHoadonByUserId(String userId) {
        List<Hoadon> hoadons = hoadonRepository.findByUserId(userId);
        return hoadons.stream().map(hoadonMapper::toHoadonResponse).toList();
    }

    public List<HoadonResponse> getAllHoadon() {
        List<Hoadon> hoadons = hoadonRepository.findAll();
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
                .ghichu(request.getGhichu())
                .noidung(request.getNoidung())
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
                .ghichu(hoadon.getGhichu())
                .noidung(request.getNoidung())
                .dob(hoadon.getDob())
                .tongtien(hoadon.getTongtien())
                .userId(user.getId())
                .build();
    }

    //
    @Transactional
    public HoadonResponse createHoadonTienmat(HoadonRequest request) {
        // Lấy thông tin người dùng
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Mã hóa đơn mới (mahd) được tạo ra bởi phương thức generateNewMahd
        String newMahd = generateNewMahd();
        DateTimeFormatter formattert = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDateT = LocalDateTime.now();
        String formattedDate = currentDateT.format(formattert);
        System.out.println("productIdsString: " + request.getProductIds());
        // Tạo và lưu hóa đơn mới vào cơ sở dữ liệu
        Hoadon hoadon = Hoadon.builder()
                .mahd(newMahd)
                .diachi(request.getDiachi())
                .tongtien(0.0) // Sẽ tính sau
                .dob(formattedDate)
                .ghichu(request.getGhichu())
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
            hoadon.setTrangthai("Thanh toán bằng tiền mặt (chưa thanh toán)"); // Cập nhật trạng thái thành công
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
                .ghichu(hoadon.getGhichu())
                .tongtien(hoadon.getTongtien())
                .userId(user.getId())
                .trangthai(hoadon.getTrangthai()) // Trả về trạng thái
                .build();
    }

    @Transactional
    public HoadonResponse createHoadon2(HoadonRequest request) {
        User user = userRepository.findFirstByEmail(request.getEmail()).stream()
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String newMahd = request.getMahd();
        LocalDateTime currentDateT = LocalDateTime.now();

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = currentDateT.format(formatter);

        Hoadon hoadon = Hoadon.builder()
                .mahd(newMahd)
                .diachi(request.getDiachi())
                .tongtien(0.0)
                .dob(formattedDate)
                .trangthai("Không thành công")
                .noidung(request.getNoidung())
                .ghichu(request.getGhichu())
                .transactionId(request.getTransactionId())
                .user(user)
                .build();
        hoadonRepository.save(hoadon);

        double tongtien = 0.0;
        List<Giohang> giohangList = request.getProductIds().stream()
                .map(giohangRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        try {
            for (Giohang giohang : giohangList) {
                List<Khodienthoai> khodienthoaiList = khodienthoaiRepository.findLatestByDienthoaiIdMausacId(
                        giohang.getDienthoai().getId(), giohang.getMausac().getId());

                if (khodienthoaiList.isEmpty()) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
                }

                Khodienthoai khodienthoai = khodienthoaiList.get(0); // Lấy sản phẩm mới nhất

                int availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
                int requestedQuantity = Integer.parseInt(giohang.getSoluong());

                if (availableQuantity < requestedQuantity) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
                }

                double giamgia = 0.0;
                List<Khuyenmai> khuyenmaiList = khuyenmaiRepository.findLatestKhuyenmaiByDienthoaiId(
                        giohang.getDienthoai().getId());

                for (Khuyenmai km : khuyenmaiList) {
                    //                    LocalDateTime ngayBatDau = LocalDateTime.parse(km.getNgaybatdau(), formatter);
                    //                    LocalDateTime ngayKetKhuc = LocalDateTime.parse(km.getNgayketkhuc(),
                    // formatter);
                    LocalDateTime ngayBatDau =
                            LocalDateTime.parse(km.getNgaybatdau(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime ngayKetKhuc =
                            LocalDateTime.parse(km.getNgayketkhuc(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                    if (!currentDateT.isBefore(ngayBatDau) && !currentDateT.isAfter(ngayKetKhuc)) {
                        giamgia = Math.max(giamgia, Double.parseDouble(km.getPhantramkhuyenmai()));
                    }
                }

                double giadienthoai = Double.parseDouble(giohang.getDienthoai().getGiaban());
                double thanhgia = giadienthoai * (1 - giamgia / 100);
                double giatong = thanhgia * requestedQuantity;

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

                khodienthoai.setSoluong(String.valueOf(availableQuantity - requestedQuantity));
                khodienthoaiRepository.save(khodienthoai);

                tongtien += giatong;
            }

            hoadon.setTongtien(tongtien);
            hoadon.setTrangthai("Thanh toán thành công chuyển khoản");
            hoadonRepository.save(hoadon);

            user.setTongtien(user.getTongtien() + tongtien);
            userRepository.save(user);

            giohangRepository.deleteAllById(request.getProductIds());

            Optional<Thongsouser> optionalThongsouser = thongsouserRepository.findByUserId(user.getId());
            Thongsouser thongsouser = optionalThongsouser.orElse(
                    Thongsouser.builder().user(user).tien(0.0).build());
            thongsouser.setTien(thongsouser.getTien() + tongtien);
            thongsouserRepository.save(thongsouser);

        } catch (AppException e) {
            hoadon.setTrangthai("Không thành công");
            hoadonRepository.save(hoadon);
            throw e;
        }

        return HoadonResponse.builder()
                .id(hoadon.getId())
                .mahd(hoadon.getMahd())
                .diachi(hoadon.getDiachi())
                .dob(hoadon.getDob())
                .ghichu(hoadon.getGhichu())
                .noidung(hoadon.getNoidung())
                .tongtien(hoadon.getTongtien())
                .userId(user.getId())
                .trangthai(hoadon.getTrangthai())
                .build();
    }

    @Transactional
    public HoadonResponse createHoadon1(HoadonRequest request) {
        // 1) Lấy user
        // Lấy user theo email từ request.
        // Nếu không tìm thấy, ném lỗi USER_NOT_EXISTED.
        // stream().findFirst() đảm bảo lấy 1 user đầu tiên (tránh trùng lặp).
        User user = userRepository.findFirstByEmail(request.getEmail()).stream()
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // 2) Tạo hóa đơn rỗng (chưa thành công)
        // Lấy thời gian hiện tại, format thành yyyy-MM-dd HH:mm:ss.
        // Tạo hóa đơn rỗng: tongtien = 0, trangthai = Không thành công.
        // Lưu hóa đơn vào DB ngay để có id và liên kết với user.
        LocalDateTime currentDateT = LocalDateTime.now();
        String formattedDate = currentDateT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Hoadon hoadon = Hoadon.builder()
                .mahd(request.getMahd())
                .diachi(request.getDiachi())
                .tongtien(0.0)
                .dob(formattedDate)
                .trangthai("Không thành công")
                .noidung(request.getNoidung())
                .ghichu(request.getGhichu())
                .transactionId(request.getTransactionId())
                .user(user)
                .build();
        hoadonRepository.save(hoadon);

        // 3) Gom giỏ hàng từ danh sách id
        // Lấy danh sách giỏ hàng từ productIds trong request.
        // Bỏ những item không tìm thấy (filter(Optional::isPresent)).
        // tongtien dùng để cộng dồn tiền sau giảm giá từng sản phẩm.

        double tongtien = 0.0;
        List<Giohang> giohangList = request.getProductIds().stream()
                .map(giohangRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        try {
            // 4) Duyệt giỏ hàng: trừ kho + tính tổng sau KM theo từng sản phẩm
            // Lấy kho điện thoại theo sản phẩm + màu sắc.
            // Nếu không có trong kho hoặc số lượng không đủ, ném lỗi SANPHAMHOADON.
            for (Giohang giohang : giohangList) {
                // Check kho
                List<Khodienthoai> khodienthoaiList = khodienthoaiRepository.findLatestByDienthoaiIdMausacId(
                        giohang.getDienthoai().getId(), giohang.getMausac().getId());
                if (khodienthoaiList.isEmpty()) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
                }
                Khodienthoai khodienthoai = khodienthoaiList.get(0);

                int availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
                int requestedQuantity = Integer.parseInt(giohang.getSoluong());
                if (availableQuantity < requestedQuantity) {
                    throw new AppException(ErrorCode.SANPHAMHOADON);
                }

                // KM theo sản phẩm (chọn % lớn nhất đang hiệu lực)
                // Lấy danh sách khuyến mãi đang áp dụng cho sản phẩm
                // Chọn % giảm giá cao nhất trong khoảng thời gian hiện tại.
                double giamgiaPct = 0.0;
                List<Khuyenmai> khuyenmaiList = khuyenmaiRepository.findLatestKhuyenmaiByDienthoaiId(
                        giohang.getDienthoai().getId());
                for (Khuyenmai km : khuyenmaiList) {
                    LocalDateTime bd = LocalDateTime.parse(km.getNgaybatdau(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime kt = LocalDateTime.parse(km.getNgayketkhuc(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    if (!currentDateT.isBefore(bd) && !currentDateT.isAfter(kt)) {
                        giamgiaPct = Math.max(giamgiaPct, parseDoubleSafe(km.getPhantramkhuyenmai()));
                    }
                }

                // giaGoc = giá gốc sản phẩm.
                // giaSauKM = giá sau khuyến mãi.
                // thanhTien = giá sau KM * số lượng mua.
                double giaGoc = parseDoubleSafe(giohang.getDienthoai().getGiaban());
                double giaSauKM = giaGoc * (1 - giamgiaPct / 100.0);
                double thanhTien = giaSauKM * requestedQuantity;

                // Lưu chi tiết hóa đơn
                // Lưu mỗi item trong giỏ hàng vào chi tiết hóa đơn.
                // giatong = tiền sau KM cho sản phẩm đó.
                Chitiethoadon cthd = Chitiethoadon.builder()
                        .dienthoai(giohang.getDienthoai())
                        .mausac(giohang.getMausac())
                        .soluong(giohang.getSoluong())
                        .giadienthoai(giaGoc)
                        .thanhgia(giaSauKM)
                        .giatong(thanhTien)
                        .giamgia(giamgiaPct)
                        .hoadon(hoadon)
                        .build();
                chitiethoadonRepository.save(cthd);

                // Trừ kho
                // Trừ số lượng trong kho.
                // Cộng tiền từng sản phẩm vào tổng hóa đơn (tongtien).
                khodienthoai.setSoluong(String.valueOf(availableQuantity - requestedQuantity));
                khodienthoaiRepository.save(khodienthoai);
                tongtien += thanhTien;
            }

            // 5) XẾP HẠNG THEO TỔNG TIỀN CỦA USER (KHÔNG CỘNG HÓA ĐƠN MỚI)  ✅ theo yêu cầu
            // Lấy tổng chi tiêu trước đó của user (lifetimeCurrent)
            // Tìm hạng user cao nhất mà tổng chi tiêu >= yêu cầu.
            double lifetimeCurrent = user.getTongtien(); // double => mặc định 0.0 nếu chưa set
            Xephanguser hang = xephanguserRepository.findAll().stream()
                    .filter(x -> lifetimeCurrent >= x.getGiatien())
                    .max(Comparator.comparingDouble(Xephanguser::getGiatien))
                    .orElse(null);

            // 6) TÍNH ƯU ĐÃI HẠNG (điều kiện áp dụng theo mô tả)
            // Lấy ưu đãi mua hàng của hạng user.
            // calcDiscountAndGetDetail tính giảm Flat + Percent trên tongtien.
            // tongSauUuDaiHang = tổng tiền sau áp ưu đãi hạng.
            double giamTheoHang = 0.0;
            DiscountCalc discountCalc = null;

            if (hang != null) {
                List<Uudaimuahang> uudaiList = uudaimuahangRepository.findAllByXephanguser_Id(hang.getId());
                // dieukienuudai áp trên tổng tiền của HÓA ĐƠN NÀY (tongtien)
                discountCalc = calcDiscountAndGetDetail(uudaiList, tongtien);
                giamTheoHang = Math.min(discountCalc.amount(), tongtien);
            }

            double tongSauUuDaiHang = tongtien - giamTheoHang;

            // 7) GHI NOIDUNG + GHI GIẢM VÀO giagiamhang  ✅ theo yêu cầu
            String noidungMoi = buildNoidungUuDai(request.getNoidung(), hang, tongtien, tongSauUuDaiHang, discountCalc);

            hoadon.setGiagiamhang(giamTheoHang); // ✅ số giảm đã áp
            hoadon.setTongtien(tongSauUuDaiHang); // tiền phải trả sau ưu đãi hạng
            hoadon.setTrangthai("Thanh toán thành công chuyển khoản");
            hoadon.setNoidung(noidungMoi);
            hoadonRepository.save(hoadon);

            // 8) Cập nhật chi tiêu user SAU khi thanh toán (cộng thêm số đã trả)
            user.setTongtien(lifetimeCurrent + tongSauUuDaiHang);
            userRepository.save(user);

            // Xóa items giỏ hàng đã thanh toán
            giohangRepository.deleteAllById(request.getProductIds());

            // Cập nhật thống số user
            Optional<Thongsouser> optTs = thongsouserRepository.findByUserId(user.getId());
            Thongsouser ts =
                    optTs.orElse(Thongsouser.builder().user(user).tien(0.0).build());
            double oldTien = ts.getTien();
            ts.setTien(oldTien + tongSauUuDaiHang);
            thongsouserRepository.save(ts);

        } catch (AppException e) {
            hoadon.setTrangthai("Không thành công");
            hoadonRepository.save(hoadon);
            throw e;
        }

        // 9) Trả response
        return HoadonResponse.builder()
                .id(hoadon.getId())
                .mahd(hoadon.getMahd())
                .diachi(hoadon.getDiachi())
                .dob(hoadon.getDob())
                .ghichu(hoadon.getGhichu())
                .noidung(hoadon.getNoidung())
                .tongtien(hoadon.getTongtien())
                .giagiamhang(hoadon.getGiagiamhang())
                .userId(user.getId().toString())
                .trangthai(hoadon.getTrangthai())
                .build();
    }

    // ========================= Helpers =========================

    // record mô tả kết quả áp ưu đãi

    // Đây là một record Java (giống class nhưng immutable, tự sinh getter).
    // Chứa thông tin kết quả áp dụng ưu đãi cho hoa đơn
    // -amount: Số tiền được giảm thực tế
    // -applicable: Danh sách tất cả ưu đãi đủ điều kiện áp dụng
    // -chosen: Ưu đãi được chọn áp dụng
    // -chosenType: "FLAT" | "PERCENT" | "NONE" | "PERCENT+FLAT"
    // -chosenPercent: Phấn trăm giảm (nếu có)
    // -chosenFlat: Số tiền giảm cố đĩnh (nếu có)
    private record DiscountCalc(
            double amount,
            List<Uudaimuahang> applicable,
            Uudaimuahang chosen,
            String chosenType, // "FLAT" | "PERCENT" | "NONE"
            double chosenPercent,
            double chosenFlat) {}

    // Tính tổng giảm giá dựa trên danh sách ưu đãi uudaiList và tổng hóa đơn tongtien.
    private DiscountCalc calcDiscountAndGetDetail(List<Uudaimuahang> uudaiList, double tongtien) {
        // Lọc ưu đãi đủ điều kiện
        // Chỉ giữ ưu đãi mà
        // Điều kiện thực thi là "muahang" hoặc "mua hang".
        // Tổng tiền hóa đơn thỏa điều kiện (>= threshold).
        // Nếu không có ưu đãi nào, trả về DiscountCalc mặc định, amount = 0.
        List<Uudaimuahang> applicable = uudaiList.stream()
                .filter(u -> isMuaHang(u.getDieukienthucthi()))
                .filter(u -> checkDieuKienUuDai(u.getDieukienuudai(), tongtien))
                .toList();

        // applicable.isEmpty()
        // applicable là danh sách các ưu đãi đủ điều kiện áp dụng cho hóa đơn.
        // Nếu danh sách này rỗng, nghĩa là không có ưu đãi nào áp dụng được cho tổng hóa đơn hiện tại.
        // ----
        // return new DiscountCalc(...)
        // Khi không có ưu đãi nào, hàm sẽ trả về một record DiscountCalc mặc định, có các giá trị sau:
        // amount = 0.0 → không giảm giá.
        // applicable = List.of() → danh sách ưu đãi đủ điều kiện rỗng.
        // chosen = null → không có ưu đãi được chọn.
        // chosenType = "NONE" → kiểu ưu đãi là NONE (không áp dụng gì cả).
        // chosenPercent = 0.0 → % giảm = 0.
        // chosenFlat = 0.0 → tiền giảm cố định = 0.
        if (applicable.isEmpty()) {
            return new DiscountCalc(0.0, List.of(), null, "NONE", 0.0, 0.0);
        }

        // Tách ra Flat và Percent
        // ưu đãi tiền cố định
        // applicable.stream() → tạo stream từ danh sách ưu đãi đủ điều kiện (applicable).
        // .filter(u -> parseDoubleSafe(u.getGiakhuyenmai()) > 0) → lọc ra các ưu đãi có giá trị tiền cố định lớn hơn 0.
        // getGiakhuyenmai() trả về số tiền ưu đãi, có thể là String, nên dùng parseDoubleSafe để chuyển sang double.
        // Nếu giá trị > 0 → nghĩa là ưu đãi này giảm tiền trực tiếp, ví dụ giảm 100,000đ.
        // .toList() → chuyển kết quả stream thành List<Uudaimuahang> flats.
        List<Uudaimuahang> flats = applicable.stream()
                .filter(u -> parseDoubleSafe(u.getGiakhuyenmai()) > 0)
                .toList();

        // ưu đãi % giảm giá
        // .filter(u -> parseDoubleSafe(u.getPhantramkhuyenmai()) > 0) → lọc ra các ưu đãi có % giảm giá lớn hơn 0.
        // getPhantramkhuyenmai() trả về chuỗi %, ví dụ "10%" → parseDoubleSafe chuyển thành số 10.0.
        // .toList() → kết quả là danh sách ưu đãi theo % giảm giá.
        List<Uudaimuahang> percents = applicable.stream()
                .filter(u -> parseDoubleSafe(u.getPhantramkhuyenmai()) > 0)
                .toList();

        // Tổng Flat
        // flats.stream() → tạo stream từ danh sách các ưu đãi tiền cố định (flats).
        // .mapToDouble(u -> parseDoubleSafe(u.getGiakhuyenmai())) → lấy ra giá trị tiền ưu đãi của từng item, chuyển từ
        // String sang double an toàn.
        // parseDoubleSafe giúp tránh lỗi nếu giá trị null hoặc không phải số.
        // .sum() → tính tổng số tiền giảm cố định từ tất cả ưu đãi.§
        // Ví dụ, nếu có 3 ưu đãi Flat: 100k, 50k, 30k → totalFlat = 180k
        double totalFlat = flats.stream()
                .mapToDouble(u -> parseDoubleSafe(u.getGiakhuyenmai()))
                .sum();
        totalFlat = Math.min(totalFlat, tongtien); // không vượt tổng tiền

        // Tổng Percent
        // percents.stream() → tạo stream từ danh sách các ưu đãi giảm giá theo phần trăm (percents).
        // .mapToDouble(u -> parseDoubleSafe(u.getPhantramkhuyenmai())) → lấy giá trị phần trăm giảm của từng ưu đãi,
        // chuyển từ String sang double an toàn.
        // parseDoubleSafe giúp tránh lỗi nếu giá trị null hoặc không phải số.
        // .max() → tìm giá trị phần trăm lớn nhất trong danh sách.
        // Vì có nhiều ưu đãi %, nhưng thông thường chỉ áp dụng ưu đãi % lớn nhất, không cộng dồn.
        // Ví dụ: nếu có ưu đãi 10%, 15%, 5% → totalPct = 15
        // .orElse(0.0) → nếu danh sách rỗng (không có ưu đãi %) thì trả 0.0.
        double totalPct = percents.stream()
                .mapToDouble(u -> parseDoubleSafe(u.getPhantramkhuyenmai()))
                .max() // có thể là cộng dồn %, nhưng thường chỉ lấy % lớn nhất
                .orElse(0.0);

        // tongtien - tổng tiền trước khi áp dụng ưu đãi
        // totaPct - phần trăm giảm giá lớn nhất
        // 1 - totalPct / 100 - hệ số giảm giá
        // tongSauPct - tổng tiền sau khi áp dụng ưu đãi %
        double tongSauPct = tongtien * (1 - totalPct / 100.0);

        // totallat tổng tiền giảm cố định từ các ưu đãi Flat
        // Trừ trực tiếp totalFlat vào tongSauPct để ra tổng tiền cuối cùng.
        // tongCuoi → tổng tiền cuối cùng khách phải trả sau cả 2 loại ưu đãi.
        double tongCuoi = tongSauPct - totalFlat;

        double amountApplied = tongtien - tongCuoi;

        // Chọn ưu đãi tham chiếu (chỉ để ghi log / record, không ảnh hưởng tính tổng)
        Uudaimuahang chosenFlat = flats.stream()
                .max(Comparator.comparingDouble(u -> parseDoubleSafe(u.getGiakhuyenmai())))
                .orElse(null);
        // lats → danh sách ưu đãi tiền cố định (Flat) đủ điều kiện.
        // .stream().max(...) → tìm ưu đãi Flat có giá trị cao nhất.
        // Comparator.comparingDouble(u -> parseDoubleSafe(u.getGiakhuyenmai())) → so sánh dựa trên giá trị tiền giảm
        // (Giakhuyenmai).
        // .orElse(null) → nếu không có ưu đãi nào thì gán null.
        // Kết quả: chosenFlat là ưu đãi Flat có số tiền giảm lớn nhất.

        Uudaimuahang chosenPct = percents.stream()
                .max(Comparator.comparingDouble(u -> parseDoubleSafe(u.getPhantramkhuyenmai())))
                .orElse(null);

        // percents → danh sách ưu đãi % (Percent) đủ điều kiện.
        // .max(...) → chọn ưu đãi % lớn nhất.
        // So sánh dựa trên Phantramkhuyenmai.
        // .orElse(null) → nếu không có ưu đãi nào thì trả về null.
        // Kết quả: chosenPct là ưu đãi giảm % lớn nhất.

        // totalPct → tổng phần trăm giảm giá (Percent).
        // totalFlat → tổng tiền giảm cố định (Flat).
        // 1. Nếu cả Percent và Flat đều > 0 → ưu đãi loại "PERCENT+FLAT".
        // 2. Nếu chỉ có Percent → "PERCENT".
        // 3. Nếu chỉ có Flat → "FLAT"
        // 4. Nếu không có ưu đãi nào → "NONE"
        // Kết quả: type mô tả loại ưu đãi đã áp dụng cho hóa đơn.
        String type;
        if (totalPct > 0 && totalFlat > 0) type = "PERCENT+FLAT";
        else if (totalPct > 0) type = "PERCENT";
        else if (totalFlat > 0) type = "FLAT";
        else type = "NONE";

        return new DiscountCalc(
                amountApplied, // số tiền thực tế đã giảm (tổng tiền trước - tổng tiền sau)
                applicable, // danh sách ưu đãi đủ điều kiện
                chosenPct != null ? chosenPct : chosenFlat, // ưu đãi tiêu biểu để ghi log / JSON
                type, // loại ưu đãi: PERCENT / FLAT / PERCENT+FLAT / NONE
                totalPct, // tổng % giảm áp dụng
                totalFlat // tổng tiền Flat áp dụng
                );
    }

    /** dieukienthucthi là "muahang"/"mua hang" (không dấu cũng nhận) */
    // Đây là hàm trả về boolean (true/false).
    // Tham số dieukienthucthi là chuỗi mô tả điều kiện thực thi của ưu đãi, ví dụ "mua hang" hoặc "muahang".
    // Nếu chuỗi rỗng/null, tức không có điều kiện, thì trả về false → không phải ưu đãi “mua hàng”.
    // normalizeVN loại bỏ dấu tiếng Việt (ví dụ mua hàng → mua hang) để dễ so sánh.
    // .toLowerCase() chuyển tất cả chữ thành chữ thường, để tránh phân biệt hoa/thường.
    // Kiểm tra xem chuỗi có chứa từ khóa “muahang” hoặc “mua hang” hay không.
    // Nếu có → trả về true, nghĩa là điều kiện ưu đãi liên quan đến mua hàng.
    // Nếu không → trả về false.
    private boolean isMuaHang(String dieukienthucthi) {
        if (dieukienthucthi == null) return false;
        String s = normalizeVN(dieukienthucthi).toLowerCase();
        return s.contains("muahang") || s.contains("mua hang");
    }

    /** Điều kiện text: ">=3000000", "> 3,000,000", "tren 3000000", "tong >= 3000000" ... áp trên TỔNG HÓA ĐƠN */
    // Đây là hàm trả về boolean (true/false).
    // Tham số: dieukienUuDai: chuỗi mô tả điều kiện, ví dụ: ">=3000000", "trên 3,000,000", "từ 5000000".
    // tongtien: tổng tiền thực tế của hóa đơn.
    private boolean checkDieuKienUuDai(String dieukienUuDai, double tongtien) {
        // Nếu điều kiện trống (null hoặc chỉ toàn khoảng trắng) → coi như không ràng buộc ⇒ mặc định true (thỏa).
        if (dieukienUuDai == null || dieukienUuDai.isBlank()) {
            return true; // không ghi điều kiện => mặc định đạt
        }
        // Chuẩn hóa chuỗi: bỏ dấu tiếng Việt, chuyển hết sang chữ thường.
        // Ví dụ: "Trên 3.000.000" → "tren 3.000.000".
        String s = normalizeVN(dieukienUuDai).toLowerCase();

        // Tìm số đầu tiên trong chuỗi (ngưỡng tiền, ví dụ 3000000).
        // Nếu không tìm thấy số hợp lệ (<= 0) → coi như không ràng buộc ⇒ true.
        double threshold = extractFirstNumber(s);
        if (threshold <= 0) return true;

        // Kiểm tra chuỗi điều kiện chứa ký hiệu/từ khóa nào để so sánh:
        // Nếu có ">=" → so sánh tongtien >= threshold.
        // Nếu có ">" hoặc từ "tren" → so sánh tongtien > threshold.
        // Nếu có "tu" → hiểu là "từ số tiền này trở lên" ⇒ tongtien >= threshold.
        if (s.contains(">=")) return tongtien >= threshold;
        if (s.contains(">")) return tongtien > threshold;
        if (s.contains("tren")) return tongtien > threshold;
        if (s.contains("tu")) return tongtien >= threshold;

        // Mặc định hiểu là ">="
        // Nếu không rơi vào các trường hợp trên thì mặc định coi điều kiện là tổng tiền ≥ threshold.
        return tongtien >= threshold;
    }

    // Loại bỏ dấu tiếng Việt khỏi chuỗi.
    // Normalizer.normalize(input, Normalizer.Form.NFD)
    // Chuyển ký tự tiếng Việt có dấu thành dạng kết hợp (ký tự gốc + ký tự dấu).
    // Ví dụ: "ấ" → "a" + "◌̂" + "◌́".
    // .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
    // Biểu thức chính quy này xóa tất cả các ký tự dấu (dấu sắc, huyền, hỏi, ngã, nặng...).
    // Sau bước này: "Ấn Độ" → "An Do".
    private String normalizeVN(String input) {
        String tmp = Normalizer.normalize(input, Normalizer.Form.NFD);
        return tmp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    // Lấy số đầu tiên xuất hiện trong chuỗi, chuyển thành double.
    // Pattern.compile("(\\d+(?:[\\.,]\\d{3})*(?:[\\.,]\\d+)?)")
    // Biểu thức regex này tìm chuỗi số, có thể chứa dấu . hoặc , (ngăn cách hàng nghìn, hoặc phần thập phân).
    // Ví dụ khớp: "3,000,000", "1.250.500", "2000.50".
    // m.find() : Kiểm tra xem có tìm thấy số nào không. Nếu có thì lấy số đầu tiên.
    // m.group(1) : Lấy giá trị số đã khớp. Ví dụ: "3,000,000".
    // .replace(".", "").replace(",", "") : Xóa bỏ dấu chấm . và dấu phẩy , (ngăn cách hàng nghìn) để tránh lỗi khi
    // parse.
    // "3,000,000" → "3000000".
    // Double.parseDouble(num) : Chuyển chuỗi "3000000" thành số thực 3000000.0.
    // Nếu có lỗi (không parse được) hoặc không tìm thấy số ⇒ trả về 0.0.
    private double extractFirstNumber(String s) {
        Matcher m = Pattern.compile("(\\d+(?:[\\.,]\\d{3})*(?:[\\.,]\\d+)?)").matcher(s);
        if (m.find()) {
            try {
                String num = m.group(1).replace(".", "").replace(",", "");
                return Double.parseDouble(num);
            } catch (NumberFormatException ignored) {
            }
        }
        return 0.0;
    }

    // Hàm này dùng để chuyển một chuỗi số (string) thành số thực (double) một cách an toàn, kể cả khi chuỗi chứa ký tự
    // phụ như %, ,, _, hoặc khoảng trắng.
    private double parseDoubleSafe(String s) {
        // Nếu chuỗi đầu vào là null thì trả về 0.0 ngay.
        // Tránh lỗi NullPointerException.
        if (s == null) return 0.0;
        try {
            // s.trim(): xóa khoảng trắng ở đầu và cuối chuỗi.
            // .replace("%", ""): loại bỏ ký hiệu phần trăm %.
            // .replace(",", ""): loại bỏ dấu phẩy (hay dùng trong số 1,000).
            // .replace("_", ""): loại bỏ ký tự _ nếu có (có thể xuất hiện trong dữ liệu nhập).
            // .replace(" ", ""): xóa mọi khoảng trắng còn sót lại.
            // Ví dụ:
            // - " 20% " → "20"
            // - "1,000 " → "1000"
            // - " 3_000 " → "3000"
            String cleaned =
                    s.trim().replace("%", "").replace(",", "").replace("_", "").replace(" ", "");
            if (cleaned.isEmpty())
                return 0.0; // Nếu sau khi làm sạch mà chuỗi rỗng (người ta nhập toàn ký tự bị loại bỏ) → trả về 0.0.
            return Double.parseDouble(
                    cleaned); // Chuyển chuỗi số sạch thành số thực (double). vd "1000" → 1000.0 - "20" → 20.0
        } catch (NumberFormatException e) {
            return 0.0; // Nếu không parse được (ví dụ "abc", "12a3") → bắt lỗi và trả về 0.0.
        }
    }

    /** Build noidung gồm ghi chú KH + block JSON-like ưu đãi thỏa & đã áp */
    // Hàm này mục đích là tạo ra chuỗi mô tả (giống JSON) để lưu vào trường noidung của hóa đơn, bao gồm thông tin ưu
    // đãi được áp dụng, các ưu đãi đủ điều kiện, tổng tiền trước/sau ưu đãi và ghi chú khách.
    private String buildNoidungUuDai(
            // ghiChuKhach: ghi chú mà khách hàng nhập vào.
            // hang: đối tượng Xephanguser (hạng của user).
            // tongTruocUuDai: tổng tiền hóa đơn trước khi áp ưu đãi.
            // tongSauUuDai: tổng tiền hóa đơn sau khi áp ưu đãi.
            // dc: kết quả tính toán ưu đãi (DiscountCalc).
            String ghiChuKhach, Xephanguser hang, double tongTruocUuDai, double tongSauUuDai, DiscountCalc dc) {
        StringBuilder sb = new StringBuilder(); // Dùng để nối chuỗi hiệu quả thay vì dùng +.

        // Ghi chú khách (nếu có)
        // Nếu khách hàng có nhập ghi chú, thì thêm dòng "Ghi chú KH: ..." vào kết quả.
        if (ghiChuKhach != null && !ghiChuKhach.isBlank()) {
            sb.append("Ghi chú KH: ").append(ghiChuKhach.trim()).append("\n");
        }

        // Bắt đầu khối JSON-like, với key gốc "uudai_hang".
        sb.append("{\"uudai_hang\":{");

        // Nếu không có hạng (hang trống), thì:
        // "hang": null → không có thông tin hạng.
        // "applicable": [] → không có ưu đãi nào đủ điều kiện.
        // "chosen": null → không chọn ưu đãi nào.
        // Ghi lại tổng tiền trước và sau ưu đãi.
        // Rồi return luôn.
        if (hang == null) {
            sb.append("\"hang\":null,");
            sb.append("\"applicable\":[],");
            sb.append("\"chosen\":null,");
            sb.append("\"tong_truoc\":").append(formatMoney(tongTruocUuDai)).append(",");
            sb.append("\"tong_sau\":").append(formatMoney(tongSauUuDai));
            sb.append("}}");
            return sb.toString();
        }

        // Ghi thông tin hạng: id, tên hạng (hangmuc), ngưỡng tiền (giatien).
        // Dùng safe() để escape ký tự đặc biệt khi in ra JSON.
        sb.append("\"hang\":{")
                .append("\"id\":")
                .append(hang.getId())
                .append(",")
                .append("\"hangmuc\":\"")
                .append(safe(hang.getHangmuc()))
                .append("\",")
                .append("\"nguong\":")
                .append(formatMoney(hang.getGiatien()))
                .append("},");

        // danh sách ưu đãi đủ điều kiện
        // Duyệt qua danh sách các ưu đãi đủ điều kiện (dc.applicable()).
        // Với mỗi ưu đãi, in ra JSON object: id, nội dung, điều kiện, phần trăm khuyến mãi, giá khuyến mãi.
        // Nếu nhiều ưu đãi thì chèn dấu ,.
        sb.append("\"applicable\":[");
        if (dc != null) {
            for (int i = 0; i < dc.applicable().size(); i++) {
                Uudaimuahang u = dc.applicable().get(i);
                if (i > 0) sb.append(",");
                sb.append("{")
                        .append("\"id\":")
                        .append(u.getId())
                        .append(",")
                        .append("\"noidung\":\"")
                        .append(safe(u.getNoidunguudai()))
                        .append("\",")
                        .append("\"dieukienuudai\":\"")
                        .append(safe(u.getDieukienuudai()))
                        .append("\",")
                        .append("\"dieukienthucthi\":\"")
                        .append(safe(u.getDieukienthucthi()))
                        .append("\",")
                        .append("\"phantramkhuyenmai\":\"")
                        .append(safe(u.getPhantramkhuyenmai()))
                        .append("\",")
                        .append("\"giakhuyenmai\":\"")
                        .append(safe(u.getGiakhuyenmai()))
                        .append("\"")
                        .append("}");
            }
        }
        sb.append("],");

        // ưu đãi đã áp
        // Nếu không có ưu đãi áp dụng → "chosen": null.
        // Nếu có ưu đãi chọn → in ra chi tiết: id, loại (PERCENT, FLAT, PERCENT+FLAT), phần trăm giảm, số tiền giảm cố
        // định, số tiền giảm thực tế đã áp.
        sb.append("\"chosen\":");
        if (dc == null || dc.chosen() == null) {
            sb.append("null,");
        } else {
            sb.append("{")
                    .append("\"id\":")
                    .append(dc.chosen().getId())
                    .append(",")
                    .append("\"type\":\"")
                    .append(dc.chosenType())
                    .append("\",")
                    .append("\"percent\":")
                    .append(dc.chosenPercent())
                    .append(",")
                    .append("\"flat\":")
                    .append(formatMoney(dc.chosenFlat()))
                    .append(",")
                    .append("\"amount_applied\":")
                    .append(formatMoney(dc.amount()))
                    .append("},");
        }

        // Ghi tổng tiền trước và sau ưu đãi.
        // Đóng các dấu ngoặc JSON.
        sb.append("\"tong_truoc\":").append(formatMoney(tongTruocUuDai)).append(",");
        sb.append("\"tong_sau\":").append(formatMoney(tongSauUuDai));
        sb.append("}}");

        return sb.toString();
    }

    // Mục đích:
    // Chuẩn bị chuỗi an toàn để đưa vào JSON, tránh lỗi cú pháp do ký tự đặc biệt.
    // Cách hoạt động:
    // if (s == null) return ""; → nếu chuỗi null, trả về chuỗi rỗng.
    // s.replace("\\", "\\\\") → thay thế \ thành \\ vì trong JSON \ phải escape.
    // .replace("\"", "\\\"") → thay dấu " thành "` để không phá vỡ JSON.
    // .replace("\n", "\\n") → thay newline thành \n để nằm trong 1 dòng JSON.
    // .replace("\r", "\\r") → thay carriage return thành \r.
    private String safe(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    // Mục đích:
    // Định dạng số tiền sang chuỗi dạng thập phân 2 chữ số, dùng dấu chấm làm phân cách thập phân (.).
    // Cách hoạt động:
    // %.2f → giữ 2 chữ số sau dấu chấm.
    // java.util.Locale.US → dùng dấu . thay vì dấu , (ví dụ ở VN là 5.000,00 nhưng ở US là 5000.00).
    private String formatMoney(double v) {
        return String.format(java.util.Locale.US, "%.2f", v);
    }

    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    /**
     * Lấy summary theo ngày chuỗi "yyyy-MM-dd".
     */
    public DailySummaryResponse getSummaryByDateString(String dateString) {
        DailySummaryResponse res = hoadonRepository.findDailySummaryByDob(dateString);
        // nếu repository trả null (không có dòng), tạo mặc định
        if (res == null) {
            return new DailySummaryResponse(dateString, 0L, 0.0);
        }
        return res;
    }

    /**
     * Lấy summary cho "today" theo timezone Asia/Ho_Chi_Minh
     */
    public DailySummaryResponse getSummaryForToday() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String dateString = today.format(fmt);
        return getSummaryByDateString(dateString);
    }

    public List<DailySummaryResponse> getAllDailySummaries() {
        return hoadonRepository.findAllDailySummaries();
    }

    public MonthlySummaryResponse getMoonthlyStatistics(int year, int month) {
        String ym = String.format("%04d-%02d", year, month);

        List<DailySummaryResponse> daily = hoadonRepository.findDailySummariesByYearMonth(ym);

        Object[] totals = hoadonRepository.findMonthlyTotalsByYearMonth(ym);

        Long totalCount = 0L;
        Double totalAmount = 0.0;

        if (totals != null && totals.length == 2) {
            if (totals[0] != null) totalCount = ((Number) totals[0]).longValue();
            if (totals[1] != null) totalAmount = ((Number) totals[1]).doubleValue();
        }

        return new MonthlySummaryResponse(year, month, totalCount, totalAmount, daily);
    }

    public HoadonSummaryResponse getHoadonSummary() {
        Object[] totals = hoadonRepository.getTotalOrdersAndAndAmount();
        Long totalOrders = 0L;
        Double totalAmount = 0.0;
        return new HoadonSummaryResponse(totalOrders, totalAmount);
    }

    public HoadonSummaryResponse getUserSummary(String userId) {
        Object[] result = hoadonRepository.getTotalOrdersAndAmountByUser(userId);
        long totalOrders = (long) result[0];
        double totalAmount = (double) result[1];
        return new HoadonSummaryResponse(totalOrders, totalAmount);
    }
}
