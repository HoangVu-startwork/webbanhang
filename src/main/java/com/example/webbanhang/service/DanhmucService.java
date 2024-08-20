package com.example.webbanhang.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.DanhmucRequest;
import com.example.webbanhang.dto.response.DanhmucResponse;
import com.example.webbanhang.entity.Danhmuc;
import com.example.webbanhang.entity.Hedieuhanh;
import com.example.webbanhang.entity.Mucluc;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.DanhmucMapper;
import com.example.webbanhang.repository.DanhmucRepository;
import com.example.webbanhang.repository.HedieuhanhRepository;
import com.example.webbanhang.repository.MuclucRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DanhmucService {
    private final DanhmucRepository danhmucRepository;
    private final MuclucRepository muclucRepository;
    private final HedieuhanhRepository hedieuhanhRepository;
    private final DanhmucMapper danhmucMapper;

    @Transactional
    public DanhmucResponse createDanhmuc(DanhmucRequest request) {
        Mucluc mucluc = muclucRepository.findByTenmucluc(request.getTenmucluc());
        Hedieuhanh hedieuhanh = hedieuhanhRepository.findByTenhedieuhanh(request.getTenhedieuhanh());
        if (danhmucRepository.findBytendanhmuc(request.getTendanhmuc()) != null) {
            throw new AppException(ErrorCode.TENDANHMUC);
        }
        if (mucluc == null) {
            throw new AppException(ErrorCode.MUCLUC);
        }
        if (hedieuhanh == null) {
            throw new AppException(ErrorCode.HEDIEUHANH);
        }

        Danhmuc danhmuc = Danhmuc.builder()
                .tendanhmuc(request.getTendanhmuc())
                .hinhanh(request.getHinhanh())
                .mucluc(mucluc)
                .hedieuhanh(hedieuhanh)
                .build();

        Danhmuc savedDanhmuc = danhmucRepository.save(danhmuc);
        return danhmucMapper.toDanhmucResponse(savedDanhmuc);
    }

    @Transactional
    public DanhmucResponse updateDanhmuc(Long id, DanhmucRequest request) {
        Danhmuc danhmuc = danhmucRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DANHMUC));

        if (request.getTenmucluc() != null && !request.getTenmucluc().isEmpty()) {
            Mucluc mucluc = muclucRepository.findByTenmucluc(request.getTenmucluc());
            if (mucluc == null) {
                throw new AppException(ErrorCode.MUCLUC);
            }
            danhmuc.setMucluc(mucluc);
        }

        if (request.getTenhedieuhanh() != null && !request.getTenhedieuhanh().isEmpty()) {
            Hedieuhanh hedieuhanh = hedieuhanhRepository.findByTenhedieuhanh(request.getTenhedieuhanh());
            if (hedieuhanh == null) {
                throw new AppException(ErrorCode.HEDIEUHANH);
            }
            danhmuc.setHedieuhanh(hedieuhanh);
        }

        if (request.getTendanhmuc() != null && !request.getTendanhmuc().isEmpty()) {
            danhmuc.setTendanhmuc(request.getTendanhmuc());
        }

        if (request.getHinhanh() != null && !request.getHinhanh().isEmpty()) {
            danhmuc.setHinhanh(request.getHinhanh());
        }

        Danhmuc updatedDanhmuc = danhmucRepository.save(danhmuc);
        return danhmucMapper.toDanhmucResponse(updatedDanhmuc);
    }

    public List<DanhmucResponse> getAllDanhmuc() {
        return danhmucRepository.findAll().stream()
                .map(danhmucMapper::toDanhmucResponse)
                .toList();
    }
}
