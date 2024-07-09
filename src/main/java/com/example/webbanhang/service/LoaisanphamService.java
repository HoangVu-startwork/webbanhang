package com.example.webbanhang.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.LoaisanphamRequest;
import com.example.webbanhang.dto.response.LoaisanphamResponse;
import com.example.webbanhang.entity.Danhmuc;
import com.example.webbanhang.entity.Loaisanpham;
import com.example.webbanhang.mapper.LoaisanphamMapper;
import com.example.webbanhang.repository.DanhmucRepository;
import com.example.webbanhang.repository.LoaisanphamRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LoaisanphamService {
    private final DanhmucRepository danhmucRepository;
    private final LoaisanphamRepository loaisanphamRepository;
    private final LoaisanphamMapper loaisanphamMapper;

    @Transactional
    public LoaisanphamResponse createLoaisanpham(LoaisanphamRequest request) {
        Danhmuc danhmuc = danhmucRepository.findBytendanhmuc(request.getTendanhmuc());

        if (loaisanphamRepository.findByTenloaisanpham(request.getTenloaisanpham()) != null) {
            throw new IllegalArgumentException("Loai san pham đã tồn tại");
        }

        if (danhmuc == null) {
            throw new IllegalArgumentException("Danh muc not found");
        }

        Loaisanpham loaisanpham = Loaisanpham.builder()
                .tenloaisanpham(request.getTenloaisanpham())
                .danhmuc(danhmuc)
                .build();

        Loaisanpham savedLoaisanpham = loaisanphamRepository.save(loaisanpham);
        return loaisanphamMapper.toLoaisanphamResponse(savedLoaisanpham);
    }

    @Transactional
    public LoaisanphamResponse updateDanhmuc(Long id, LoaisanphamRequest request) {
        Loaisanpham loaisanpham = loaisanphamRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("không tồn tại not found"));

        Danhmuc danhmuc = danhmucRepository.findBytendanhmuc(request.getTendanhmuc());

        if (danhmuc == null) {
            throw new IllegalArgumentException("Danh muc not found");
        }

        loaisanpham.setTenloaisanpham(request.getTenloaisanpham());
        loaisanpham.setDanhmuc(danhmuc);

        Loaisanpham updatedLoaidanhmuc = loaisanphamRepository.save(loaisanpham);
        return loaisanphamMapper.toLoaisanphamResponse(updatedLoaidanhmuc);
    }
}
