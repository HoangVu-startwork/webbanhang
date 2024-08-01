package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
