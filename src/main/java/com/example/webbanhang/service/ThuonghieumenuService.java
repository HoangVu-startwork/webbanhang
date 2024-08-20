package com.example.webbanhang.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.response.ThuonghieumenuResponse;
import com.example.webbanhang.mapper.ThuonghieumenuMapper;
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

    public List<ThuonghieumenuResponse> getAllThuonghieumenu() {
        Pageable pageable = PageRequest.of(0, 5); // Trang đầu tiên và 5 bản ghi mỗi trang
        return thuonghieumenuRepository.findByTinhtrang("mở", pageable).stream()
                .map(thuonghieumenuMapper::toThuonghieumenuResponse)
                .toList();
    }
}
