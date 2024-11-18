package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.ThuonghieudienthoaiRequest;
import com.example.webbanhang.dto.response.ThuonghieudienthoaiResponse;
import com.example.webbanhang.dto.response.ThuonghieudienthoaisResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thuonghieudienthoai;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.ThuonghieudienthoaiMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThuonghieudienthoaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ThuonghieudienthoaiService {

    ThuonghieudienthoaiRepository thuonghieudienthoaiRepository;
    ThuonghieudienthoaiMapper thuonghieudienthoaiMapper;
    DienthoaiRepository dienthoaiRepository;

    public ThuonghieudienthoaisResponse findThuonghieudienthoai(Long id) {
        Thuonghieudienthoai thuonghieudienthoai =
                thuonghieudienthoaiRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DANHMUC));
        return thuonghieudienthoaiMapper.toThuonghieudienthoaisResponse(thuonghieudienthoai);
    }

    public ThuonghieudienthoaisResponse toThuonghieudienthoaisResponse(Thuonghieudienthoai thuonghieudienthoai) {
        return ThuonghieudienthoaisResponse.builder()
                .id(thuonghieudienthoai.getId())
                .tenthuonghieu(thuonghieudienthoai.getTenthuonghieu())
                .hinhanh(thuonghieudienthoai.getHinhanh())
                .dienthoaiId(thuonghieudienthoai.getDienthoai().getId())
                .tinhtrang(thuonghieudienthoai.getTinhtrang())
                .dob(thuonghieudienthoai.getDob())
                .tenSanPham(thuonghieudienthoai.getDienthoai().getTensanpham()) // Ensure this is mapped correctly
                .build();
    }

    public List<ThuonghieudienthoaisResponse> getAllThuonghieudienthoai() {
        return thuonghieudienthoaiRepository.findAll().stream()
                .map(thuonghieudienthoaiMapper::toThuonghieudienthoaisResponse)
                .toList();
    }

    public List<ThuonghieudienthoaisResponse> getAllThuonghieudienthoaiweb() {
        List<Thuonghieudienthoai> thuonghieuList = thuonghieudienthoaiRepository.findByTinhtrang("Mở");
        return thuonghieuList.stream()
                .map(thuonghieudienthoaiMapper::toThuonghieudienthoaisResponse)
                .toList();
    }

    public ThuonghieudienthoaiResponse addThuonghieudienthoai(ThuonghieudienthoaiRequest request) {
        Dienthoai dienthoai = dienthoaiRepository
                .findById(request.getDienthoaiId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Không tìm thấy điện thoại với ID: " + request.getDienthoaiId()));

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        Thuonghieudienthoai thuonghieudienthoai = Thuonghieudienthoai.builder()
                .tenthuonghieu(request.getTenthuonghieu())
                .hinhanh(request.getHinhanh())
                .dienthoai(dienthoai)
                .tinhtrang(request.getTinhtrang())
                .dob(formattedDateTime)
                .build();

        thuonghieudienthoai = thuonghieudienthoaiRepository.save(thuonghieudienthoai);
        return thuonghieudienthoaiMapper.toThuonghieudienthoaiResponse(thuonghieudienthoai);
    }

    public ThuonghieudienthoaiResponse updateThuonghieudienthoai(Long id, ThuonghieudienthoaiRequest request) {
        Thuonghieudienthoai thuonghieudienthoai = thuonghieudienthoaiRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu điện thoại với ID: " + id));

        //        Dienthoai dienthoai = dienthoaiRepository
        //                .findById(request.getDienthoaiId())
        //                .orElseThrow(() ->
        //                        new EntityNotFoundException("Không tìm thấy điện thoại với ID: " +
        // request.getDienthoaiId()));

        if (request.getTinhtrang() != null && !request.getTinhtrang().isEmpty()) {
            thuonghieudienthoai.setTinhtrang(request.getTinhtrang());
        }

        if (request.getTenthuonghieu() != null && !request.getTenthuonghieu().isEmpty()) {
            thuonghieudienthoai.setTenthuonghieu(request.getTenthuonghieu());
        }

        if (request.getHinhanh() != null && !request.getHinhanh().isEmpty()) {
            thuonghieudienthoai.setHinhanh(request.getHinhanh());
        }

        if (request.getDob() != null && !request.getDob().isEmpty()) {
            thuonghieudienthoai.setDob(request.getDob());
        }

        if (request.getDienthoaiId() != null
                && !request.getDienthoaiId()
                        .equals(thuonghieudienthoai.getDienthoai().getId())) {
            Dienthoai dienthoai = dienthoaiRepository
                    .findById(request.getDienthoaiId())
                    .orElseThrow(() -> new RuntimeException("Dienthoai not found"));
            thuonghieudienthoai.setDienthoai(dienthoai);
        }

        thuonghieudienthoai = thuonghieudienthoaiRepository.save(thuonghieudienthoai);
        return thuonghieudienthoaiMapper.toThuonghieudienthoaiResponse(thuonghieudienthoai);
    }

    public void deletethuonghieudienthoai(Long id) {
        thuonghieudienthoaiRepository.deleteById(id);
    }
}
