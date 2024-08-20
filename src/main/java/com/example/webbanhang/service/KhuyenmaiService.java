package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.KhuyenmaiRequest;
import com.example.webbanhang.dto.response.KhuyenmaiResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Khuyenmai;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.KhuyenmaiMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.KhuyenmaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class KhuyenmaiService {
    DienthoaiRepository dienthoaiRepository;
    KhuyenmaiRepository khuyenmaiRepository;
    KhuyenmaiMapper khuyenmaiMapper;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public KhuyenmaiResponse saveKhuyenmai(KhuyenmaiRequest request) {
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
        if (dienthoai == null) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }

        Khuyenmai khuyenmai = khuyenmaiMapper.toKhuyenmai(request);
        khuyenmai.setDienthoai(dienthoai);
        khuyenmai.setNgaybatdau(String.valueOf(LocalDateTime.parse(request.getNgaybatdau(), DATE_TIME_FORMATTER)));
        khuyenmai.setNgayketkhuc(String.valueOf(LocalDateTime.parse(request.getNgayketkhuc(), DATE_TIME_FORMATTER)));

        Khuyenmai saveKhuyenmai = khuyenmaiRepository.save(khuyenmai);

        return khuyenmaiMapper.toKhuyenmaiResponse(saveKhuyenmai);
    }

    private DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Định dạng ngày tháng bạn đang sử dụng

    public String getPhanTramKhuyenMai(Dienthoai dienthoai) {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        List<Khuyenmai> khuyenmaiList = khuyenmaiRepository.findByDienthoai(dienthoai);

        for (Khuyenmai km : khuyenmaiList) {
            LocalDateTime startDateTime = LocalDateTime.parse(km.getNgaybatdau(), formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(km.getNgayketkhuc(), formatter);

            // now.isEqual(startDateTime): So sánh toàn bộ giá trị ngày tháng năm và giờ phút giây của now với
            // startDateTime. Nếu toàn bộ các thành phần này đều bằng nhau, thì kết quả sẽ là true.
            //
            // now.isAfter(startDateTime): So sánh toàn bộ giá trị của now với startDateTime. Nếu now có ngày tháng năm
            // hoặc giờ phút giây lớn hơn startDateTime, thì kết quả sẽ là true.
            //
            // now.isBefore(endDateTime): Tương tự, so sánh toàn bộ giá trị của now với endDateTime. Nếu now có ngày
            // tháng năm hoặc giờ phút giây nhỏ hơn endDateTime, thì kết quả sẽ là true.
            if ((!today.isBefore(startDateTime) && !today.isAfter(endDateTime))) {
                return km.getPhantramkhuyenmai();
            }
        }
        return null; // Không có khuyến mãi cho điện thoại này
    }
}
