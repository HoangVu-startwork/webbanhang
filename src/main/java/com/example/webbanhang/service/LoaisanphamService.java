package com.example.webbanhang.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.LoaisanphamRequest;
import com.example.webbanhang.dto.response.LoaisanphamResponse;
import com.example.webbanhang.entity.Danhmuc;
import com.example.webbanhang.entity.Loaisanpham;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
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
            throw new AppException(ErrorCode.LOAISANPHAMTONTAI);
        }

        if (danhmuc == null) {
            throw new AppException(ErrorCode.DANHMUC);
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
        Loaisanpham loaisanpham =
                loaisanphamRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LOAISANPHAM));

        if (request.getTendanhmuc() != null && !request.getTendanhmuc().isEmpty()) {
            Danhmuc danhmuc = danhmucRepository.findBytendanhmuc(request.getTendanhmuc());
            if (danhmuc == null) {
                throw new AppException(ErrorCode.MUCLUC);
            }
            loaisanpham.setDanhmuc(danhmuc);
        }

        if (request.getTenloaisanpham() != null && !request.getTenloaisanpham().isEmpty()) {
            loaisanpham.setTenloaisanpham(request.getTenloaisanpham());
        }

        Loaisanpham updatedLoaisanpham = loaisanphamRepository.save(loaisanpham);
        return loaisanphamMapper.toLoaisanphamResponse(updatedLoaisanpham);
    }

    public LoaisanphamResponse findById(Long id) {
        Loaisanpham loaisanpham =
                loaisanphamRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LOAISANPHAM));
        return loaisanphamMapper.toLoaisanphamResponse(loaisanpham);
    }

    public LoaisanphamResponse findByTenloaisanpham(String tenloaisanpham) {
        Loaisanpham loaisanpham = loaisanphamRepository.findByTenloaisanpham(tenloaisanpham);
        return loaisanphamMapper.toLoaisanphamResponse(loaisanpham);
    }

    public List<LoaisanphamResponse> findAllLoaisanpham() {
        List<Loaisanpham> loaisanphams = loaisanphamRepository.findAll();
        return loaisanphams.stream()
                .map(loaisanphamMapper::toLoaisanphamResponse)
                .toList();
    }

    public List<LoaisanphamResponse> getLoaisanphamByDanhmucId(Long danhmucId) {
        List<Loaisanpham> loaisanphams = loaisanphamRepository.findByDanhmucId(danhmucId);
        return loaisanphams.stream()
                .map(loaisanpham -> LoaisanphamResponse.builder()
                        .id(loaisanpham.getId())
                        .tenloaisanpham(loaisanpham.getTenloaisanpham())
                        .danhmucId(loaisanpham.getDanhmuc().getId())
                        .build())
                .toList();
    }
}
