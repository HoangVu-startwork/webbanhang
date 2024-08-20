package com.example.webbanhang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.response.ThuonghieudienthoaiResponse;
import com.example.webbanhang.mapper.ThuonghieudienthoaiMapper;
import com.example.webbanhang.repository.ThuonghieudienthoaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ThuonghieudienthoaiService {

    ThuonghieudienthoaiRepository thuonghieudienthoaiRepository;
    ThuonghieudienthoaiMapper thuonghieudienthoaiMapper;

    public List<ThuonghieudienthoaiResponse> getAllThuonghieudienthoai() {
        return thuonghieudienthoaiRepository.findAll().stream()
                .map(thuonghieudienthoaiMapper::toThuonghieudienthoaiResponse)
                .toList();
    }
}
