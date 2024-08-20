package com.example.webbanhang.service;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.NhucaudienthoaiRequest;
import com.example.webbanhang.dto.response.NhucaudienthoaiResponse;
import com.example.webbanhang.entity.Nhucaudienthoai;
import com.example.webbanhang.mapper.NhucaudienthoaiMapper;
import com.example.webbanhang.repository.NhucaudienthoaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NhucaudienthoaiService {
    NhucaudienthoaiRepository nhucaudienthoaiRepository;
    NhucaudienthoaiMapper nhucaudienthoaiMapper;

    public NhucaudienthoaiResponse createNhucaudienthoai(NhucaudienthoaiRequest request) {
        // Kiểm tra xem nhu cầu điện thoại đã tồn tại chưa
        if (nhucaudienthoaiRepository.existsByTennhucau(request.getTennhucau())) {
            throw new IllegalArgumentException("Nhu cầu điện thoại đã tồn tại với tên này.");
        }

        Nhucaudienthoai nhucaudienthoai = nhucaudienthoaiMapper.toNhucaudienthoai(request);
        Nhucaudienthoai savedNhucaudienthoai = nhucaudienthoaiRepository.save(nhucaudienthoai);

        return nhucaudienthoaiMapper.toNhucaudienthoaiResponse(savedNhucaudienthoai);
    }
}
