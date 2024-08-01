package com.example.webbanhang.service;

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
}
