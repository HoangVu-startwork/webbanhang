package com.example.webbanhang.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.ThongsokythuatRequest;
import com.example.webbanhang.dto.response.ThongsokythuatResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thongsokythuat;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.ThongsokythuatMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThongsokythuatRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongsokythuatService {
    private final DienthoaiRepository dienthoaiRepository;
    private final ThongsokythuatRepository thongsokythuatRepository;
    private final ThongsokythuatMapper thongsokythuatMapper;

    @Transactional
    public ThongsokythuatResponse createThongsokythuat(ThongsokythuatRequest request) {
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
        if (dienthoai == null) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }

        if (thongsokythuatRepository.findByDienthoaiId(dienthoai.getId()) != null) {
            throw new AppException(ErrorCode.THONGSOKYTHUAT);
        }

        Thongsokythuat thongsokythuat = thongsokythuatMapper.toThongsokythuat(request);
        thongsokythuat.setDienthoai(dienthoai);
        Thongsokythuat savedThongsokythuat = thongsokythuatRepository.save(thongsokythuat);
        return thongsokythuatMapper.toThongsokythuatResponse(savedThongsokythuat);
    }
}
