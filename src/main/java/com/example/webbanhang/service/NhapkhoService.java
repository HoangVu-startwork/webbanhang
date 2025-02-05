package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.NhapkhosRequest;
import com.example.webbanhang.dto.response.NhapkhosResponse;
import com.example.webbanhang.entity.*;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.NhapkhoMapper;
import com.example.webbanhang.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NhapkhoService {
    NhapkhoRepository nhapkhoRepository;
    KhodienthoaiRepository khodienthoaiRepository;
    NhapkhoMapper nhapkhoMapper;
    DienthoaiRepository dienthoaiRepository;
    MausacRepository mausacRepository;
    UserRepository userRepository;

    @Transactional
    public NhapkhosResponse nhapKho(NhapkhosRequest request) {
        // Retrieve Dienthoai and Mausac objects using their IDs from the request
        Dienthoai dienthoai = dienthoaiRepository.findByid(request.getDienthoaiId());
        if (dienthoai == null) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }

        // Tìm thông tin màu sắc
        Mausac mausac = mausacRepository.findByDienthoai_IdAndId(dienthoai.getId(), request.getMausacId());
        if (mausac == null) {
            throw new AppException(ErrorCode.MAUSAC);
        }

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Convert request to entity
        Nhapkho nhapkho = nhapkhoMapper.toGiohangs(request);

        // Set Dienthoai and Mausac in Nhapkho entity
        nhapkho.setDienthoai(dienthoai);
        nhapkho.setMausac(mausac);

        // Set current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        nhapkho.setDob(formattedDateTime);
        nhapkho.setUser(user);

        // Find Khodienthoai by dienthoaiId and mausacId
        Khodienthoai existingKhodienthoai = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
                nhapkho.getDienthoai().getId(), nhapkho.getMausac().getId());

        if (existingKhodienthoai != null) {
            // Update existing Khodienthoai
            int updatedSoluong =
                    Integer.parseInt(existingKhodienthoai.getSoluong()) + Integer.parseInt(request.getSoluong());
            existingKhodienthoai.setSoluong(String.valueOf(updatedSoluong));

            int updatedSoluongtong =
                    Integer.parseInt(existingKhodienthoai.getTongsoluong()) + Integer.parseInt(request.getSoluong());
            existingKhodienthoai.setTongsoluong(String.valueOf(updatedSoluongtong));

            khodienthoaiRepository.save(existingKhodienthoai);
        } else {
            // Create new Khodienthoai
            Khodienthoai newKhodienthoai = Khodienthoai.builder()
                    .dienthoai(nhapkho.getDienthoai())
                    .mausac(nhapkho.getMausac())
                    .soluong(request.getSoluong())
                    .tongsoluong(request.getSoluong())
                    .build();
            khodienthoaiRepository.save(newKhodienthoai);
        }

        // Save Nhapkho
        Nhapkho savedNhapkho = nhapkhoRepository.save(nhapkho);

        // Convert to response
        return nhapkhoMapper.toNhapkhoResponse(savedNhapkho);
    }

    @Transactional
    public NhapkhosResponse updateNhapkho(Long id, NhapkhosRequest request) {
        // Lấy entity Nhapkho hiện có
        // lấy thông tin của Nhapkho dựa trên id được truyền vào.
        // Nếu không tìm thấy Nhapkho, nó sẽ ném ra một ngoại lệ (RuntimeException)
        Nhapkho existingNhapkho = nhapkhoRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NHAPKHO));

        // Lấy đối tượng Dienthoai và Mausac cũ từ entity Nhapkho hiện có
        Dienthoai oldDienthoai = existingNhapkho.getDienthoai();
        Mausac oldMausac = existingNhapkho.getMausac();

        int oldSoluong = Integer.parseInt(existingNhapkho.getSoluong()); // Lưu trữ số lượng (soluong) cũ
        // để chuyển đổi một chuỗi ký tự (string) thành một số nguyên (int)

        // Cập nhật Dienthoai nếu được cung cấp
        if (request.getDienthoaiId() != null) {
            Dienthoai newDienthoai =
                    dienthoaiRepository.findByid(request.getDienthoaiId()); // tìm dienthoai dựa vào tensanpham
            if (newDienthoai == null) {
                throw new AppException(ErrorCode.TENDIENTHOAI);
            }
            existingNhapkho.setDienthoai(newDienthoai);

            // Nếu Dienthoai thay đổi, cập nhật số lượng trong Khodienthoai tương ứng
            if (!oldDienthoai.equals(newDienthoai)) {
                updateKhodienthoaiQuantity(
                        oldDienthoai, oldMausac, -oldSoluong); // -oldSoluong là giá trị âm của oldSoluong
                updateKhodienthoaiQuantity(newDienthoai, oldMausac, oldSoluong);
            }
        }

        // Cập nhật Mausac nếu được cung cấp
        if (request.getMausacId() != null) {
            Mausac newMausac = mausacRepository.findByDienthoai_IdAndId(
                    existingNhapkho.getDienthoai().getId(), request.getMausacId());
            if (newMausac == null) {
                throw new AppException(ErrorCode.MAUSAC);
            }
            existingNhapkho.setMausac(newMausac);

            // Nếu Mausac thay đổi, cập nhật số lượng trong Khodienthoai tương ứng
            if (!oldMausac.equals(newMausac)) {
                updateKhodienthoaiQuantity(existingNhapkho.getDienthoai(), oldMausac, -oldSoluong);
                updateKhodienthoaiQuantity(existingNhapkho.getDienthoai(), newMausac, oldSoluong);
            }
        }

        // Cập nhật soluong nếu được cung cấp

        if (request.getSoluong() != null) {
            int newSoluong = Integer.parseInt(request.getSoluong());
            // cập nhật số lượng tồn kho (Khodienthoai) và cập nhật soluong cho Nhapkho.
            // Điều này xác định số lượng tồn kho cần tăng hoặc giảm.
            // Nếu newSoluong lớn hơn oldSoluong, quantityChange sẽ là số dương và số lượng tồn kho sẽ tăng.
            //        Nếu newSoluong nhỏ hơn oldSoluong, quantityChange sẽ là số âm và số lượng tồn kho sẽ giảm.
            updateKhodienthoaiQuantity(
                    existingNhapkho.getDienthoai(), existingNhapkho.getMausac(), newSoluong - oldSoluong);
            existingNhapkho.setSoluong(request.getSoluong());
        }

        // Lưu entity Nhapkho đã được cập nhật
        Nhapkho updatedNhapkho = nhapkhoRepository.save(existingNhapkho);

        // Chuyển đổi thành response
        return nhapkhoMapper.toNhapkhoResponse(updatedNhapkho);
    }

    private void updateKhodienthoaiQuantity(Dienthoai dienthoai, Mausac mausac, int quantityChange) {
        // Phương thức sử dụng dienthoai.getId() và mausac.getId() để tìm đối tượng Khodienthoai tương ứng trong cơ sở
        // dữ liệu.
        Khodienthoai khodienthoai =
                khodienthoaiRepository.findByDienthoaiIdAndMausacId(dienthoai.getId(), mausac.getId());
        if (khodienthoai != null) {
            // quantityChange là giá trị dương, số lượng tồn kho sẽ tăng.
            // quantityChange là giá trị âm, số lượng tồn kho sẽ giảm.
            int updatedSoluong = Integer.parseInt(khodienthoai.getSoluong()) + quantityChange;
            int updatedSoluongtong = Integer.parseInt(khodienthoai.getTongsoluong()) + quantityChange;
            khodienthoai.setSoluong(String.valueOf(updatedSoluong));
            khodienthoai.setTongsoluong(String.valueOf(updatedSoluongtong));
            khodienthoaiRepository.save(khodienthoai);
        }
    }

    public List<NhapkhosResponse> findAllNhapkho() {
        List<Nhapkho> nhapkho = nhapkhoRepository.findAll();
        return nhapkho.stream().map(nhapkhoMapper::toNhapkhoResponse).toList();
    }

    public NhapkhosResponse getNhapkho(Long id) {
        Nhapkho nhapkho =
                nhapkhoRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NHAPKHO_NOT_FOUND));

        return nhapkhoMapper.toNhapkhoResponse(nhapkho);
    }
}
