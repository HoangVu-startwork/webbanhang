package com.example.webbanhang.service;

import java.util.List;
import java.util.NoSuchElementException;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.UudaimuahangRequest;
import com.example.webbanhang.dto.response.UudaimuahangResponse;
import com.example.webbanhang.entity.Uudaimuahang;
import com.example.webbanhang.entity.Xephanguser;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.UudaimuahangMapper;
import com.example.webbanhang.repository.UudaimuahangRepository;
import com.example.webbanhang.repository.XephanguserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UudaimuahangService {

    UudaimuahangRepository uudaimuahangRepository;
    XephanguserRepository xephanguserRepository;
    UudaimuahangMapper uudaimuahangMapper;

    @Transactional
    public UudaimuahangResponse create(UudaimuahangRequest request) {
        Xephanguser xephanguser = xephanguserRepository
                .findById(request.getXephanguserId())
                .orElseThrow(() -> new AppException(ErrorCode.DANHMUC));

        Uudaimuahang uudaimuahang = uudaimuahangMapper.toEntity(request);
        uudaimuahang.setXephanguser(xephanguser);
        uudaimuahang = uudaimuahangRepository.save(uudaimuahang);
        return uudaimuahangMapper.toResponse(uudaimuahang);
    }

    public UudaimuahangResponse getById(Long id) {
        Uudaimuahang uudaimuahang = uudaimuahangRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return uudaimuahangMapper.toResponse(uudaimuahang);
    }

    public List<UudaimuahangResponse> getByXephanguser(Long xephanguserId) {
        return uudaimuahangRepository
                .findByXephanguser(
                        xephanguserRepository.findById(xephanguserId).orElseThrow(() -> new NoSuchElementException()))
                .stream()
                .map(uudaimuahangMapper::toResponse)
                .toList();
    }

    public List<UudaimuahangResponse> getAll() {
        return uudaimuahangRepository.findAll().stream()
                .map(uudaimuahangMapper::toResponse)
                .toList();
    }

    public UudaimuahangResponse update(Long id, UudaimuahangRequest request) {
        Uudaimuahang uudaimuahang = uudaimuahangRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        uudaimuahang = uudaimuahangMapper.toEntity(request);
        uudaimuahang = uudaimuahangRepository.save(uudaimuahang);
        return uudaimuahangMapper.toResponse(uudaimuahang);
    }

    public String delete(Long id) {
        uudaimuahangRepository.deleteById(id);
        return null;
    }
}
