package com.example.webbanhang.service;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.KhodienthoaiRequest;
import com.example.webbanhang.dto.response.KhodienthoaiResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Khodienthoai;
import com.example.webbanhang.entity.Mausac;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.KhodienthoaiMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.KhodienthoaiRepository;
import com.example.webbanhang.repository.MausacRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class KhodienthoaiService {
    private final DienthoaiRepository dienthoaiRepository;
    private final MausacRepository mausacRepository;
    private final KhodienthoaiMapper khodienthoaiMapper;
    private final KhodienthoaiRepository khodienthoaiRepository;

    public KhodienthoaiResponse addToCart(KhodienthoaiRequest request) {
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
        if (dienthoai == null) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }

        // Tìm thông tin màu sắc
        Mausac mausac = mausacRepository.findByDienthoaiIdAndTenmausac(dienthoai.getId(), request.getTenmausac());
        if (mausac == null) {
            throw new AppException(ErrorCode.MAUSAC);
        }

        Khodienthoai existingKhodienthoai =
                khodienthoaiRepository.findByDienthoaiIdAndMausacId(dienthoai.getId(), mausac.getId());
        if (existingKhodienthoai != null) {
            throw new AppException(ErrorCode.MAUSACVSDIENTHOAI);
        }

        Khodienthoai khodienthoai = khodienthoaiMapper.toKhodienthoai(request);
        khodienthoai.setDienthoai(dienthoai);
        khodienthoai.setMausac(mausac);
        Khodienthoai savedKhodienthoai = khodienthoaiRepository.save(khodienthoai);
        return khodienthoaiMapper.toKhodienthoaiResponse(savedKhodienthoai);
    }
}
