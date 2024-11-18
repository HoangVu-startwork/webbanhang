package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.ThuonghieumenuRequest;
import com.example.webbanhang.dto.response.ThuonghieumenuResponse;
import com.example.webbanhang.dto.response.ThuonghieumenusResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thuonghieumenu;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.ThuonghieumenuMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThuonghieumenuRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ThuonghieumenuService {

    ThuonghieumenuRepository thuonghieumenuRepository;
    ThuonghieumenuMapper thuonghieumenuMapper;
    DienthoaiRepository dienthoaiRepository;

    public List<ThuonghieumenuResponse> getAllThuonghieumenu() {
        Pageable pageable = PageRequest.of(0, 5); // Trang đầu tiên và 5 bản ghi mỗi trang
        return thuonghieumenuRepository.findByTinhtrang("Mở", pageable).stream()
                .map(thuonghieumenuMapper::toThuonghieumenuResponse)
                .toList();
    }

    public List<ThuonghieumenusResponse> getAllThuonghieumenuAd() {
        return thuonghieumenuRepository.findAll().stream()
                .map(thuonghieumenuMapper::toThuonghieumenusResponse)
                .toList();
    }

    public ThuonghieumenusResponse findThuonghieumenudienthoai(Long id) {
        Thuonghieumenu thuonghieumenu =
                thuonghieumenuRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DANHMUC));
        return thuonghieumenuMapper.toThuonghieumenusResponse(thuonghieumenu);
    }

    public ThuonghieumenusResponse toThuonghieumenusResponse(Thuonghieumenu thuonghieumenu) {
        return ThuonghieumenusResponse.builder()
                .id(thuonghieumenu.getId())
                .label(thuonghieumenu.getLabel())
                .text(thuonghieumenu.getText())
                .hinhanh(thuonghieumenu.getHinhanh())
                .dienthoaiId(thuonghieumenu.getDienthoai().getId())
                .tinhtrang(thuonghieumenu.getTinhtrang())
                .dob(thuonghieumenu.getDob())
                .tenSanPham(thuonghieumenu.getDienthoai().getTensanpham()) // Ensure this is mapped correctly
                .build();
    }

    public ThuonghieumenuResponse addThuonghieumenu(ThuonghieumenuRequest request) {
        Dienthoai dienthoai = dienthoaiRepository
                .findById(request.getDienthoaiId())
                .orElseThrow(() -> new RuntimeException("Dienthoai not found"));

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        Thuonghieumenu thuonghieumenu = Thuonghieumenu.builder()
                .hinhanh(request.getHinhanh())
                .label(request.getLabel())
                .text(request.getText())
                .tinhtrang(request.getTinhtrang())
                .dob(formattedDateTime)
                .dienthoai(dienthoai)
                .build();
        Thuonghieumenu saved = thuonghieumenuRepository.save(thuonghieumenu);
        return thuonghieumenuMapper.toThuonghieumenuResponse(saved);
    }

    public ThuonghieumenuResponse updateThuonghieumenu(Long id, ThuonghieumenuRequest request) {
        Thuonghieumenu existing = thuonghieumenuRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Thuonghieumenu not found"));

        if (request.getHinhanh() != null && !request.getHinhanh().isEmpty()) {
            existing.setHinhanh(request.getHinhanh());
        }

        if (request.getLabel() != null && !request.getLabel().isEmpty()) {
            existing.setLabel(request.getLabel());
        }

        if (request.getText() != null && !request.getText().isEmpty()) {
            existing.setText(request.getText());
        }

        if (request.getDob() != null && !request.getDob().isEmpty()) {
            existing.setDob(request.getDob());
        }

        if (request.getTinhtrang() != null && !request.getTinhtrang().isEmpty()) {
            existing.setTinhtrang(request.getTinhtrang());
        }

        if (request.getDienthoaiId() != null
                && !request.getDienthoaiId().equals(existing.getDienthoai().getId())) {
            Dienthoai dienthoai = dienthoaiRepository
                    .findById(request.getDienthoaiId())
                    .orElseThrow(() -> new RuntimeException("Dienthoai not found"));
            existing.setDienthoai(dienthoai);
        }

        Thuonghieumenu updated = thuonghieumenuRepository.save(existing);
        return thuonghieumenuMapper.toThuonghieumenuResponse(updated);
    }

    public void deletethuonghieumenudienthoai(Long id) {
        thuonghieumenuRepository.deleteById(id);
    }
}
