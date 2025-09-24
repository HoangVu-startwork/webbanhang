package com.example.webbanhang.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.XephanguserRequest;
import com.example.webbanhang.dto.response.XephanguserResponse;
import com.example.webbanhang.entity.Xephanguser;
import com.example.webbanhang.mapper.XephanguserMapper;
import com.example.webbanhang.repository.XephanguserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class XephanguserService {
    XephanguserRepository xephanguserRepository;
    XephanguserMapper xephanguserMapper;

    public XephanguserResponse create(XephanguserRequest request) {
        Xephanguser xephanguser = xephanguserMapper.toEntity(request);
        xephanguser = xephanguserRepository.save(xephanguser);
        return xephanguserMapper.toResponse(xephanguser);
    }

    @Transactional
    public List<XephanguserResponse> getAll() {
        return xephanguserRepository.findAll().stream()
                .map(xephanguserMapper::toResponse)
                .toList();
    }

    public XephanguserResponse update(Long id, XephanguserRequest request) {
        Xephanguser xephanguser = xephanguserRepository.findById(id).orElseThrow();
        xephanguser = xephanguserMapper.toEntity(request);
        xephanguser = xephanguserRepository.save(xephanguser);
        return xephanguserMapper.toResponse(xephanguser);
    }

    public XephanguserResponse getById(Long id) {
        Xephanguser xephanguser = xephanguserRepository.findById(id).orElseThrow();
        return xephanguserMapper.toResponse(xephanguser);
    }

    public String delete(Long id) {
        xephanguserRepository.deleteById(id);
        return null;
    }
}
