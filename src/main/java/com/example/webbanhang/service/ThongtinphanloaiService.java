package com.example.webbanhang.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.ThongtinphanloaiRequest;
import com.example.webbanhang.dto.response.ThongtinphanloaiResponse;
import com.example.webbanhang.entity.Loaisanpham;
import com.example.webbanhang.entity.Thongtinphanloai;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
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
    DienthoaiService dienThoaiService;

    @Transactional
    public ThongtinphanloaiResponse createThongtinphanloai(ThongtinphanloaiRequest request) {
        Loaisanpham loaisanpham = loaisanphamRepository.findByTenloaisanpham(request.getTenloaisanpham());
        if (thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai()) != null) {
            throw new AppException(ErrorCode.THONGTINPHANLOAI);
        }
        if (loaisanpham == null) {
            throw new AppException(ErrorCode.LOAISANPHAM);
        }

        Thongtinphanloai thongtinphanloai = Thongtinphanloai.builder()
                .tenphanloai(request.getTenphanloai())
                .loaisanpham(loaisanpham)
                .build();

        Thongtinphanloai savedThongtinphanloai = thongtinphanloaiRepository.save(thongtinphanloai);
        return thongtinphanloaiMapper.toThongtinphanloaiResponse(savedThongtinphanloai);
    }

    public List<ThongtinphanloaiResponse> getByThongtinphanloai(Long loaisanphamId) {
        List<Thongtinphanloai> thongtinphanloais = thongtinphanloaiRepository.findByLoaisanphamId(loaisanphamId);
        return thongtinphanloais.stream()
                .map(thongtinphanloai -> ThongtinphanloaiResponse.builder()
                        .id(thongtinphanloai.getId())
                        .tenphanloai(thongtinphanloai.getTenphanloai())
                        .loaisanphamId(thongtinphanloai.getLoaisanpham().getId())
                        .build())
                .toList();
    }
}
