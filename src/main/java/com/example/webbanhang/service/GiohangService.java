package com.example.webbanhang.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.GiohangRequest;
import com.example.webbanhang.dto.response.GiohangResponse;
import com.example.webbanhang.entity.*;
import com.example.webbanhang.mapper.GiohangMapper;
import com.example.webbanhang.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GiohangService {
    DienthoaiRepository dienthoaiRepository;
    MausacRepository mausacRepository;
    GiohangRepository giohangRepository;
    GiohangMapper giohangMapper;
    UserRepository userRepository;
    KhodienthoaiRepository khodienthoaiRepository;

    public GiohangResponse addToCart(GiohangRequest giohangRequest) {
        // Find phone information
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(giohangRequest.getTensanpham());
        if (dienthoai == null) {
            throw new RuntimeException("Product not found");
        }

        // Find color information
        Mausac mausac =
                mausacRepository.findByDienthoaiIdAndTenmausac(dienthoai.getId(), giohangRequest.getTenmausac());
        if (mausac == null) {
            throw new RuntimeException("Color not found");
        }

        // Find user information
        User user = userRepository
                .findByEmail(giohangRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check stock quantity in Khodienthoai
        Khodienthoai khodienthoai =
                khodienthoaiRepository.findByDienthoaiIdAndMausacId(dienthoai.getId(), mausac.getId());
        if (khodienthoai == null) {
            throw new RuntimeException("Stock not found");
        }

        int availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
        int requestedQuantity = Integer.parseInt(giohangRequest.getSoluong());

        if (availableQuantity < requestedQuantity) {
            throw new RuntimeException("Not enough stock available");
        }

        // Check if phone and color are already in the cart
        Optional<Giohang> existingCartItem = giohangRepository.findByUserId_AndDienthoaiId_And_MausacId(
                user.getId(), dienthoai.getId(), mausac.getId());

        Giohang giohang;
        if (existingCartItem.isPresent()) {
            // If already present, increase quantity
            giohang = existingCartItem.get();
            int newQuantity = Integer.parseInt(giohang.getSoluong()) + requestedQuantity;
            giohang.setSoluong(String.valueOf(newQuantity));
        } else {
            // If not present, create new cart item
            giohang = Giohang.builder()
                    .dienthoai(dienthoai)
                    .mausac(mausac)
                    .user(user)
                    .soluong(giohangRequest.getSoluong())
                    .build();
        }

        // Save cart item
        Giohang savedGiohang = giohangRepository.save(giohang);

        // Create response
        return GiohangResponse.builder()
                .dienthoaiId(savedGiohang.getDienthoai().getId())
                .mausacId(savedGiohang.getMausac().getId())
                .soluong(savedGiohang.getSoluong())
                .userId(savedGiohang.getUser().getId())
                .build();
    }

    public void removeFromCart(Long giohangId) {
        // Check if the item exists in the cart
        // Sử dụng phương thức findById của giohangRepository để tìm kiếm mục giỏ hàng với ID đã cho.
        // Kết quả trả về là một đối tượng Optional<Giohang>, cho phép chúng ta kiểm tra xem mục giỏ hàng có tồn tại hay
        // không.
        Optional<Giohang> existingCartItem = giohangRepository.findById(giohangId);

        if (existingCartItem
                .isPresent()) { // Phương thức isPresent của Optional kiểm tra xem đối tượng có giá trị hay không
            // If exists, delete the item
            giohangRepository.deleteById(giohangId);
            // Nếu mục giỏ hàng tồn tại, phương thức deleteById của giohangRepository sẽ được gọi để xóa mục giỏ hàng
            // khỏi cơ sở dữ liệu dựa trên ID.
        } else {
            throw new RuntimeException("Item not found in cart");
        }
    }
}
