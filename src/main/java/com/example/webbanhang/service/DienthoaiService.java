package com.example.webbanhang.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.response.DienthoaiResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thongtinphanloai;
import com.example.webbanhang.mapper.DienthoaiMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThongtinphanloaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DienthoaiService {
    private final DienthoaiRepository dienthoaiRepository;
    private final DienthoaiMapper dienthoaiMapper;
    private final ThongtinphanloaiRepository thongtinphanloaiRepository;

    @Transactional
    public DienthoaiResponse createDienthoai(DienthoaiRequest request) {
        Thongtinphanloai thongtinphanloai = thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai());

        if (dienthoaiRepository.findByTensanpham(request.getTensanpham()) != null) {
            throw new IllegalArgumentException("Dien thoai đã tồn tại");
        }

        if (thongtinphanloai == null) {
            throw new IllegalArgumentException("Danh muc not found");
        }

        Dienthoai dienthoai = Dienthoai.builder()
                .tensanpham(request.getTensanpham())
                .hinhanh(request.getHinhanh())
                .hinhanhduyet(request.getHinhanhduyet())
                .bonho(request.getBonho())
                .ram(request.getRam())
                .giaban(request.getGiaban())
                .thongtinphanloai(thongtinphanloai)
                .build();

        Dienthoai savedDienthoai = dienthoaiRepository.save(dienthoai);
        return dienthoaiMapper.toDienthoaiResponse(savedDienthoai);
    }

    @Transactional
    public DienthoaiResponse updateDienthoai(Long id, DienthoaiRequest request) {
        Dienthoai dienthoai = dienthoaiRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("không tồn tại not found"));

        if (request.getTenphanloai() != null && !request.getTenphanloai().isEmpty()) {
            Thongtinphanloai thongtinphanloai = thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai());
            if (thongtinphanloai == null) {
                throw new IllegalArgumentException("Danh muc not found");
            }
            dienthoai.setThongtinphanloai(thongtinphanloai);
        }

        if (request.getTensanpham() != null && !request.getTensanpham().isEmpty()) {
            dienthoai.setTensanpham(request.getTensanpham());
        }
        if (request.getRam() != null && !request.getRam().isEmpty()) {
            dienthoai.setRam(request.getRam());
        }
        if (request.getBonho() != null && !request.getBonho().isEmpty()) {
            dienthoai.setBonho(request.getBonho());
        }
        if (request.getGiaban() != null && !request.getGiaban().isEmpty()) {
            dienthoai.setGiaban(request.getGiaban());
        }
        if (request.getHinhanh() != null && !request.getHinhanh().isEmpty()) {
            dienthoai.setHinhanh(request.getHinhanh());
        }
        if (request.getHinhanhduyet() != null && !request.getHinhanhduyet().isEmpty()) {
            dienthoai.setHinhanhduyet(request.getHinhanhduyet());
        }

        Dienthoai updatedDienthoai = dienthoaiRepository.save(dienthoai);
        return dienthoaiMapper.toDienthoaiResponse(updatedDienthoai);
    }
}
