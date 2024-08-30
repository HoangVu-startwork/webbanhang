package com.example.webbanhang.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.MausacRequest;
import com.example.webbanhang.dto.response.MausacResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Mausac;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.MausacMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.MausacRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MausacService {
    private final DienthoaiRepository dienthoaiRepository;
    private final MausacRepository mausacRepository;
    private final MausacMapper mausacMapper;

    public MausacResponse createMausac(MausacRequest request) {
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
        if (dienthoai == null) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }
        // Kiểm tra xem điện thoại đã có màu đó chưa
        Mausac existingMausac =
                mausacRepository.findByDienthoaiIdAndTenmausac(dienthoai.getId(), request.getTenmausac());
        if (existingMausac != null) {
            throw new AppException(ErrorCode.MAUSACTONTAI);
        }
        Mausac mausac = mausacMapper.toMausac(request);
        mausac.setDienthoai(dienthoai);
        Mausac savedMausac = mausacRepository.save(mausac);
        return mausacMapper.toMausacResponse(savedMausac);
    }

    @Transactional
    public MausacResponse updateMausac(Long id, MausacRequest request) {
        Mausac mausac = mausacRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MAUSAC));

        if (request.getTenmausac() != null && !request.getTenmausac().isEmpty()) {
            // Kiểm tra nếu tenmausac đã tồn tại cho dienthoaiId hiện tại
            Mausac existingMausac = mausacRepository.findByDienthoaiIdAndTenmausac(
                    mausac.getDienthoai().getId(), request.getTenmausac());
            if (existingMausac != null && !existingMausac.getId().equals(mausac.getId())) {
                throw new AppException(ErrorCode.MAUSACTONTAITRONGDIENTHOAI);
            }
            mausac.setTenmausac(request.getTenmausac());
        }

        if (request.getTensanpham() != null && !request.getTensanpham().isEmpty()) {
            Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
            if (dienthoai == null) {
                throw new AppException(ErrorCode.TENDIENTHOAI);
            }

            // Kiểm tra nếu màu sắc mới đã tồn tại cho điện thoại mới
            Mausac existingMausac =
                    mausacRepository.findByDienthoaiIdAndTenmausac(dienthoai.getId(), request.getTenmausac());
            if (existingMausac != null && !existingMausac.getId().equals(mausac.getId())) {
                throw new AppException(ErrorCode.MAUSACTONTAITRONGDIENTHOAI);
            }
            mausac.setDienthoai(dienthoai);
        }

        if (request.getGiaban() != null && !request.getGiaban().isEmpty()) {
            mausac.setGiaban(request.getGiaban());
        }

        if (request.getHinhanh() != null && !request.getHinhanh().isEmpty()) {
            mausac.setHinhanh(request.getHinhanh());
        }

        Mausac updatedMausac = mausacRepository.save(mausac);
        return mausacMapper.toMausacResponse(updatedMausac);
    }

    public void deleteMausac(Long id) {
        mausacRepository.deleteById(id);
    }

    public List<MausacResponse> getAllDanhmuc() {
        return mausacRepository.findAll().stream()
                .map(mausacMapper::toMausacResponse)
                .toList();
    }

    public MausacResponse getmausacId(Long id) {
        Mausac mausacs = mausacRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MAUSAC));
        return mausacMapper.toMausacResponse(mausacs);
    }
}
