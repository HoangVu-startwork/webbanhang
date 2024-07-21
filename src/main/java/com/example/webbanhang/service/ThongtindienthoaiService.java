package com.example.webbanhang.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.ThongtindienthoaiRequest;
import com.example.webbanhang.dto.response.ThongtindienthoaiResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thongtindienthoai;
import com.example.webbanhang.mapper.ThongtindienthoaiMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThongtindienthoaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongtindienthoaiService {
    private final DienthoaiRepository dienthoaiRepository;
    private final ThongtindienthoaiRepository thongtindienthoaiRepository;
    private final ThongtindienthoaiMapper thongtindienthoaiMapper;

    //    @Transactional
    //    public ThongtindienthoaiResponse createOrUpdateThongtindienthoai(
    //            Long dienthoaiId, ThongtindienthoaiRequest request) {
    //        Thongtindienthoai existingThongtindienthoai = thongtindienthoaiRepository.findByDienthoaiId(dienthoaiId);
    //
    //        if (existingThongtindienthoai != null) {
    //            // Cập nhật thông tin nếu đã tồn tại
    //            existingThongtindienthoai.setTinhtrangmay(request.getTinhtrangmay());
    //            existingThongtindienthoai.setThietbidikem(request.getThietbidikem());
    //            existingThongtindienthoai.setBaohanh(request.getBaohanh());
    //            Thongtindienthoai updatedThongtindienthoai =
    // thongtindienthoaiRepository.save(existingThongtindienthoai);
    //            return thongtindienthoaiMapper.toThongtindienthoaiResponse(updatedThongtindienthoai);
    //        } else {
    //            // Tạo mới thông tin điện thoại nếu chưa tồn tại
    //            Dienthoai dienthoai = dienthoaiRepository
    //                    .findById(dienthoaiId)
    //                    .orElseThrow(() -> new IllegalArgumentException("Dienthoai not found with id: " +
    // dienthoaiId));
    //
    //            Thongtindienthoai thongtindienthoai = thongtindienthoaiMapper.toThongtindienthoai(request);
    //            thongtindienthoai.setDienthoai(dienthoai);
    //            Thongtindienthoai savedThongtindienthoai = thongtindienthoaiRepository.save(thongtindienthoai);
    //            return thongtindienthoaiMapper.toThongtindienthoaiResponse(savedThongtindienthoai);
    //        }
    //    }

    @Transactional
    public ThongtindienthoaiResponse createThongtindienthoai(ThongtindienthoaiRequest request) {
        if (request.getTensanpham() == null || request.getTensanpham().isEmpty()) {
            throw new IllegalArgumentException("Tensanpham is required and cannot be empty");
        }
        if (request.getBaohanh() == null || request.getBaohanh().isEmpty()) {
            throw new IllegalArgumentException("Baohanh is required and cannot be empty");
        }
        if (request.getThietbidikem() == null || request.getThietbidikem().isEmpty()) {
            throw new IllegalArgumentException("Thietbidikem is required and cannot be empty");
        }
        if (request.getTinhtrangmay() == null || request.getTinhtrangmay().isEmpty()) {
            throw new IllegalArgumentException("Tinhtrangmay is required and cannot be empty");
        }
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
        if (dienthoai == null) {
            throw new IllegalArgumentException("Dienthoai not found with tensanpham: " + request.getTensanpham());
        }

        if (thongtindienthoaiRepository.findByDienthoaiId(dienthoai.getId()) != null) {
            throw new IllegalArgumentException(
                    "Thongtindienthoai already exists for dienthoaiId: " + dienthoai.getId());
        }

        Thongtindienthoai thongtindienthoai = thongtindienthoaiMapper.toThongtindienthoai(request);
        thongtindienthoai.setDienthoai(dienthoai);
        Thongtindienthoai savedThongtindienthoai = thongtindienthoaiRepository.save(thongtindienthoai);
        return thongtindienthoaiMapper.toThongtindienthoaiResponse(savedThongtindienthoai);
    }

    //    @Transactional
    //    public ThongtindienthoaiResponse updateThongtindienthoai(Long id, ThongtindienthoaiRequest request) {
    //        Thongtindienthoai thongtindienthoai = thongtindienthoaiRepository
    //                .findById(id)
    //                .orElseThrow(() -> new IllegalArgumentException("không tồn tại not found"));
    //
    //        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
    //        if (request.getTensanpham() != null && !request.getTensanpham().isEmpty()) {
    //            if (dienthoai == null) {
    //                throw new IllegalArgumentException("Danh muc not found");
    //            }
    //
    //            thongtindienthoai.setDienthoai(dienthoai);
    //        }
    //
    //        if (request.getBaohanh() != null && !request.getBaohanh().isEmpty()) {
    //            thongtindienthoai.setBaohanh(request.getBaohanh());
    //        }
    //
    //        if (request.getThietbidikem() != null && !request.getThietbidikem().isEmpty()) {
    //            thongtindienthoai.setThietbidikem(request.getThietbidikem());
    //        }
    //
    //        if (request.getTinhtrangmay() != null && !request.getTinhtrangmay().isEmpty()) {
    //            thongtindienthoai.setTinhtrangmay(request.getTinhtrangmay());
    //        }
    //
    //        Thongtindienthoai updatedThongtindienthoai = thongtindienthoaiRepository.save(thongtindienthoai);
    //        return thongtindienthoaiMapper.toThongtindienthoaiResponse(updatedThongtindienthoai);
    //    }
    @Transactional
    public ThongtindienthoaiResponse updateThongtindienthoai(Long id, ThongtindienthoaiRequest request) {
        Thongtindienthoai thongtindienthoai = thongtindienthoaiRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("không tồn tại not found"));

        // Kiểm tra nếu tensanpham được cung cấp
        if (request.getTensanpham() != null && !request.getTensanpham().isEmpty()) {
            Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());

            // Nếu tensanpham mới không tồn tại trong bảng dienthoai
            if (dienthoai == null) {
                throw new IllegalArgumentException("Dienthoai not found");
            }

            // Kiểm tra nếu tensanpham mới đã tồn tại trong bảng thongtindienthoai
            Thongtindienthoai existingThongtindienthoai =
                    thongtindienthoaiRepository.findByDienthoaiId(dienthoai.getId());
            if (existingThongtindienthoai != null
                    && !existingThongtindienthoai.getId().equals(thongtindienthoai.getId())) {
                throw new IllegalArgumentException("Thongtindienthoai with this tensanpham already exists");
            }

            thongtindienthoai.setDienthoai(dienthoai);
        }

        if (request.getBaohanh() != null && !request.getBaohanh().isEmpty()) {
            thongtindienthoai.setBaohanh(request.getBaohanh());
        }

        if (request.getThietbidikem() != null && !request.getThietbidikem().isEmpty()) {
            thongtindienthoai.setThietbidikem(request.getThietbidikem());
        }

        if (request.getTinhtrangmay() != null && !request.getTinhtrangmay().isEmpty()) {
            thongtindienthoai.setTinhtrangmay(request.getTinhtrangmay());
        }

        Thongtindienthoai updatedThongtindienthoai = thongtindienthoaiRepository.save(thongtindienthoai);
        return thongtindienthoaiMapper.toThongtindienthoaiResponse(updatedThongtindienthoai);
    }
}
