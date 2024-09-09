package com.example.webbanhang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.KetnoinhucauRequest;
import com.example.webbanhang.dto.request.NhucaudienthoaiRequest;
import com.example.webbanhang.dto.response.KetnoinhucauResponse;
import com.example.webbanhang.dto.response.NhucaudienthoaiResponse;
import com.example.webbanhang.entity.Ketnoinhucau;
import com.example.webbanhang.entity.Nhucaudienthoai;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.KetnoinhucauMapper;
import com.example.webbanhang.mapper.NhucaudienthoaiMapper;
import com.example.webbanhang.repository.KetnoinhucauRepository;
import com.example.webbanhang.repository.NhucaudienthoaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NhucaudienthoaiService {
    NhucaudienthoaiRepository nhucaudienthoaiRepository;
    NhucaudienthoaiMapper nhucaudienthoaiMapper;
    KetnoinhucauRepository ketnoinhucauRepository;
    KetnoinhucauMapper ketnoinhucauMapper;

    public NhucaudienthoaiResponse createNhucaudienthoai(NhucaudienthoaiRequest request) {
        // Kiểm tra xem nhu cầu điện thoại đã tồn tại chưa
        if (nhucaudienthoaiRepository.existsByTennhucau(request.getTennhucau())) {
            throw new AppException(ErrorCode.NHUCAUDIENTHOAI);
        }

        Nhucaudienthoai nhucaudienthoai = nhucaudienthoaiMapper.toNhucaudienthoai(request);
        Nhucaudienthoai savedNhucaudienthoai = nhucaudienthoaiRepository.save(nhucaudienthoai);

        return nhucaudienthoaiMapper.toNhucaudienthoaiResponse(savedNhucaudienthoai);
    }

    public NhucaudienthoaiResponse getNhucaudienthoai(Long id) {
        Nhucaudienthoai nhucaudienthoai =
                nhucaudienthoaiRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return nhucaudienthoaiMapper.toNhucaudienthoaiResponse(nhucaudienthoai);
    }

    public List<NhucaudienthoaiResponse> getAllNhucaudienthoai() {
        return nhucaudienthoaiRepository.findAll().stream()
                .map(nhucaudienthoaiMapper::toNhucaudienthoaiResponse)
                .toList();
    }

    public KetnoinhucauResponse addKetnoinhucau(KetnoinhucauRequest request) {
        Long dienthoaiId = request.getDienthoaiId();
        Long nhucaudienthoaiId = request.getNhucaudienthoaiId();

        if (ketnoinhucauRepository.existsByDienthoaiIdAndNhucaudienthoaiId(dienthoaiId, nhucaudienthoaiId)) {
            throw new AppException(ErrorCode.KETNOINHUCAU);
        } else {
            Ketnoinhucau ketnoinhucau = ketnoinhucauMapper.toKetnoinhucau(request);
            Ketnoinhucau savedketnoinhucau = ketnoinhucauRepository.save(ketnoinhucau);
            return ketnoinhucauMapper.toKetnoinhucauResponse(savedketnoinhucau);
        }
    }

    public KetnoinhucauResponse getKetnoinhucau(Long id) {
        Ketnoinhucau ketnoinhucau =
                ketnoinhucauRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return ketnoinhucauMapper.toKetnoinhucauResponse(ketnoinhucau);
    }
}
