package com.example.webbanhang.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.ThongtinphanloaiRequest;
import com.example.webbanhang.dto.response.ThongtinphanloaiResponse;
import com.example.webbanhang.entity.Loaisanpham;
import com.example.webbanhang.entity.Thongtinphanloai;
import com.example.webbanhang.mapper.ThongtinphanloaiMapper;
import com.example.webbanhang.repository.LoaisanphamRepository;
import com.example.webbanhang.repository.ThongtinphanloaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ThongtinphanloaiService {
    ThongtinphanloaiRepository thongtinphanloaiRepository;
    ThongtinphanloaiMapper thongtinphanloaiMapper;
    LoaisanphamRepository loaisanphamRepository;

    @Transactional
    public ThongtinphanloaiResponse createThongtinphanloai(ThongtinphanloaiRequest request) {
        Loaisanpham loaisanpham = loaisanphamRepository.findByTenloaisanpham(request.getTenloaisanpham());
        if (thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai()) != null) {
            throw new IllegalArgumentException("Thông tin phan loai đã tồn tại");
        }
        if (loaisanpham == null) {
            throw new IllegalArgumentException("Thongtin phan loai not found");
        }

        Thongtinphanloai thongtinphanloai = Thongtinphanloai.builder()
                .tenphanloai(request.getTenphanloai())
                .loaisanpham(loaisanpham)
                .build();

        Thongtinphanloai savedThongtinphanloai = thongtinphanloaiRepository.save(thongtinphanloai);
        return thongtinphanloaiMapper.toThongtinphanloaiResponse(savedThongtinphanloai);
    }
}
