package com.example.webbanhang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.MuclucRequest;
import com.example.webbanhang.dto.response.MuclucResponse;
import com.example.webbanhang.entity.Mucluc;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.MuclucMapper;
import com.example.webbanhang.repository.MuclucRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MuclucService {
    MuclucRepository muclucRepository;

    MuclucMapper muclucMapper;

    public MuclucResponse create(MuclucRequest request) {
        if (muclucRepository.findByTenmucluc(request.getTenmucluc()) != null) {
            throw new AppException(ErrorCode.MUCLUCTONTAI);
        }
        Mucluc mucluc = muclucMapper.toMucluc(request);
        Mucluc savedMucluc = muclucRepository.save(mucluc);
        return muclucMapper.toMuclucResponse(savedMucluc); // Bạn cần phương thức để chuyển Mucluc thành MuclucResponse
    }

    public MuclucResponse updateMucluc(Long id, MuclucRequest request) {
        Mucluc mucluc = muclucRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MUCLUCTONTAI));

        if (request.getTenmucluc() != null && !request.getTenmucluc().isEmpty()) {
            mucluc.setTenmucluc(request.getTenmucluc());
        }

        Mucluc updatedMucluc = muclucRepository.save(mucluc);
        return muclucMapper.toMuclucResponse(updatedMucluc);
    }

    public List<MuclucResponse> getAllDanhmuc() {
        return muclucRepository.findAll().stream()
                .map(muclucMapper::toMuclucResponse)
                .toList();
    }

    public MuclucResponse getByTenmucluc(Long id) {
        Mucluc mucluc = muclucRepository.findByid(id);
        if (mucluc == null) {
            throw new AppException(ErrorCode.MUCLUCTONTAI);
        }
        return muclucMapper.toMuclucResponse(mucluc);
    }

    public void deleteMucluc(Long id) {
        muclucRepository.deleteById(id);
    }
}
