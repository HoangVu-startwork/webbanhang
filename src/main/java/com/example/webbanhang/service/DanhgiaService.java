package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.DanhgiaRequest;
import com.example.webbanhang.dto.response.DanhgiaResponse;
import com.example.webbanhang.dto.response.DanhgiasaoResponse;
import com.example.webbanhang.entity.Danhgia;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.DanhgiaMapper;
import com.example.webbanhang.repository.DanhgiaRepository;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DanhgiaService {

    DanhgiaRepository danhgiaRepository;
    DienthoaiRepository dienthoaiRepository;
    UserRepository userRepository;
    DanhgiaMapper danhgiaMapper;

    public DanhgiaResponse createDanhgia(DanhgiaRequest request) {
        Long dienthoaiId = request.getDienthoaiId();
        String userId = request.getUserId();
        if (!dienthoaiRepository.existsById(dienthoaiId)) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = currentDate.format(formatter);

        Danhgia danhgia = danhgiaMapper.toDanhgia(request);
        danhgia.setDob(formattedDate);
        danhgia.setDienthoai(
                dienthoaiRepository.findById(dienthoaiId).orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI)));
        danhgia.setUser(
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        Danhgia savedDanhgia = danhgiaRepository.save(danhgia);
        return danhgiaMapper.toDanhgiaResponse(
                savedDanhgia); // Bạn cần phương thức để chuyển Mucluc thành MuclucResponse
    }

    public DanhgiaResponse getCommentsByDienthoaiId(Long dienthoaiId) {
        if (!dienthoaiRepository.existsById(dienthoaiId)) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }
        Danhgia danhmucs = danhgiaRepository.findByDienthoaiId(dienthoaiId);
        return danhgiaMapper.toDanhgiaResponse(danhmucs);
    }

    public DanhgiasaoResponse getDanhgiasa(Long dienthoaiId) {
        if (!dienthoaiRepository.existsById(dienthoaiId)) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }

        Long namsao = danhgiaRepository.count5sao(dienthoaiId);
        Long bonsao = danhgiaRepository.count4sao(dienthoaiId);
        Long basao = danhgiaRepository.count3sao(dienthoaiId);
        Long haisao = danhgiaRepository.count2sao(dienthoaiId);
        Long motsao = danhgiaRepository.count1sao(dienthoaiId);
        Double tongsao = danhgiaRepository.findTongsao(dienthoaiId);

        DanhgiasaoResponse danhgiasaoResponse = DanhgiasaoResponse.builder()
                .dienthoaiId(dienthoaiId)
                .namsao(namsao)
                .bonsao(bonsao)
                .motsao(motsao)
                .haisao(haisao)
                .basao(basao)
                .tongsao(tongsao)
                .build();
        return danhgiasaoResponse;
    }
}
