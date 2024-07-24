package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.NhapkhoRequest;
import com.example.webbanhang.dto.response.NhapkhoResponse;
import com.example.webbanhang.entity.*;
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
    public NhapkhoResponse nhapKho(NhapkhoRequest request) {
        // Retrieve Dienthoai and Mausac objects using their IDs from the request
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
        if (dienthoai == null) {
            throw new RuntimeException("Product not found");
        }

        // Tìm thông tin màu sắc
        Mausac mausac = mausacRepository.findByDienthoaiIdAndTenmausac(dienthoai.getId(), request.getTenmausac());
        if (mausac == null) {
            throw new RuntimeException("Color not found");
        }

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert request to entity
        Nhapkho nhapkho = nhapkhoMapper.toGiohang(request);

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
            khodienthoaiRepository.save(existingKhodienthoai);
        } else {
            // Create new Khodienthoai
            Khodienthoai newKhodienthoai = Khodienthoai.builder()
                    .dienthoai(nhapkho.getDienthoai())
                    .mausac(nhapkho.getMausac())
                    .soluong(request.getSoluong())
                    .build();
            khodienthoaiRepository.save(newKhodienthoai);
        }

        // Save Nhapkho
        Nhapkho savedNhapkho = nhapkhoRepository.save(nhapkho);

        // Convert to response
        return nhapkhoMapper.toNhapkhoResponse(savedNhapkho);
    }

    @Transactional
    public NhapkhoResponse updateNhapkho(Long id, NhapkhoRequest request) {
        // Lấy entity Nhapkho hiện có
        // lấy thông tin của Nhapkho dựa trên id được truyền vào.
        // Nếu không tìm thấy Nhapkho, nó sẽ ném ra một ngoại lệ (RuntimeException)
        Nhapkho existingNhapkho =
                nhapkhoRepository.findById(id).orElseThrow(() -> new RuntimeException("Nhapkho không tìm thấy"));

        // Lấy đối tượng Dienthoai và Mausac cũ từ entity Nhapkho hiện có
        Dienthoai oldDienthoai = existingNhapkho.getDienthoai();
        Mausac oldMausac = existingNhapkho.getMausac();

        int oldSoluong = Integer.parseInt(existingNhapkho.getSoluong()); // Lưu trữ số lượng (soluong) cũ
        // để chuyển đổi một chuỗi ký tự (string) thành một số nguyên (int)

        // Cập nhật Dienthoai nếu được cung cấp
        if (request.getTensanpham() != null) {
            Dienthoai newDienthoai =
                    dienthoaiRepository.findByTensanpham(request.getTensanpham()); // tìm dienthoai dựa vào tensanpham
            if (newDienthoai == null) {
                throw new RuntimeException("Dienthoai không tìm thấy");
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
        if (request.getTenmausac() != null) {
            Mausac newMausac = mausacRepository.findByDienthoaiIdAndTenmausac(
                    existingNhapkho.getDienthoai().getId(), request.getTenmausac());
            if (newMausac == null) {
                throw new RuntimeException("Mausac không tìm thấy");
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
            khodienthoai.setSoluong(String.valueOf(updatedSoluong));
            khodienthoaiRepository.save(khodienthoai);
        }
    }
}
