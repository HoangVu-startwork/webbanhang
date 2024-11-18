package com.example.webbanhang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.HedieuhanhRequest;
import com.example.webbanhang.dto.response.HedieuhanhResponse;
import com.example.webbanhang.entity.Hedieuhanh;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.HedieuhanhMapper;
import com.example.webbanhang.repository.HedieuhanhRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HedieuhanhService {
    HedieuhanhRepository hedieuhanhRepository;

    HedieuhanhMapper hedieuhanhMapper;

    public HedieuhanhResponse create(HedieuhanhRequest request) {
        if (hedieuhanhRepository.findByTenhedieuhanh(request.getTenhedieuhanh()) != null) {
            throw new AppException(ErrorCode.HEDIEUHANHTONTAI);
        }
        Hedieuhanh hedieuhanh = hedieuhanhMapper.toHedieuhanh(request);
        Hedieuhanh savedHedieuhanh = hedieuhanhRepository.save(hedieuhanh);
        return hedieuhanhMapper.toHedieuhanhResponse(
                savedHedieuhanh); // Bạn cần phương thức để chuyển Mucluc thành MuclucResponse
    }

    public HedieuhanhResponse updateHedieuhanh(Long id, HedieuhanhRequest request) {
        Hedieuhanh hedieuhanh =
                hedieuhanhRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.HEDIEUHANH));

        if (request.getTenhedieuhanh() != null && !request.getTenhedieuhanh().isEmpty()) {
            hedieuhanh.setTenhedieuhanh(request.getTenhedieuhanh());
        }

        Hedieuhanh updatedHedieuhanh = hedieuhanhRepository.save(hedieuhanh);
        return hedieuhanhMapper.toHedieuhanhResponse(updatedHedieuhanh);
    }

    //    public List<HedieuhanhResponse> getAllHedieuhanh() {
    //        return hedieuhanhRepository.findAll().stream()
    //                .map(hedieuhanhMapper::toHedieuhanhResponse)
    //                .toList();
    //    }

    public List<HedieuhanhResponse> findAllhedieuhanh() {
        List<Hedieuhanh> hedieuhanhs = hedieuhanhRepository.findAll();
        return hedieuhanhs.stream().map(hedieuhanhMapper::toHedieuhanhResponse).toList();
    }

    public void deleteHedieuhanh(Long id) {
        hedieuhanhRepository.deleteById(id);
    }

    public HedieuhanhResponse getHedieuhanhById(Long id) {
        Hedieuhanh hedieuhanh =
                hedieuhanhRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.HEDIEUHANH));
        return hedieuhanhMapper.toHedieuhanhResponse(hedieuhanh);
    }
}
