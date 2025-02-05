package com.example.webbanhang.service;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.GiohangRequest;
import com.example.webbanhang.dto.response.GiohangResponse;
import com.example.webbanhang.entity.*;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
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
    KhuyenmaiService khuyenmaiService;

    public GiohangResponse addToCart(GiohangRequest giohangRequest) {
        // Find phone information
        Dienthoai dienthoai = dienthoaiRepository.findByid(giohangRequest.getDienthoaiId());
        if (dienthoai == null) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }

        // Find color information
        Mausac mausac = mausacRepository.findByDienthoai_IdAndId(dienthoai.getId(), giohangRequest.getMausacId());
        if (mausac == null) {
            throw new AppException(ErrorCode.MAUSAC);
        }

        // Find user information
        User user = userRepository
                .findByEmail(giohangRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        // Check stock quantity in Khodienthoai
        Khodienthoai khodienthoai =
                khodienthoaiRepository.findByDienthoaiIdAndMausacId(dienthoai.getId(), mausac.getId());
        if (khodienthoai == null) {
            throw new AppException(ErrorCode.KHOHANG);
        }

        int availableQuantity = Integer.parseInt(khodienthoai.getSoluong());
        int requestedQuantity = Integer.parseInt(giohangRequest.getSoluong());

        if (availableQuantity == 0) {
            throw new AppException(ErrorCode.KHOHANG);
        }

        if (availableQuantity < requestedQuantity) {
            throw new AppException(ErrorCode.KHOHANGGIOHANG);
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

    @PostConstruct
    public void init() {
        updateCartQuantityBasedOnStock();
    }

    @Scheduled(fixedRate = 60000)
    public void updateCartQuantityBasedOnStock() {
        List<Giohang> cartItems = giohangRepository.findAll();
        for (Giohang cartItem : cartItems) {
            Khodienthoai stock = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
                    cartItem.getDienthoai().getId(), cartItem.getMausac().getId());

            if (stock != null) {
                int availableQuantity = Integer.parseInt(stock.getSoluong());
                int cartQuantity = Integer.parseInt(cartItem.getSoluong());

                if (availableQuantity == 0) {
                    cartItem.setSoluong("0");
                } else if (availableQuantity < cartQuantity) {
                    cartItem.setSoluong(String.valueOf(availableQuantity));
                }

                giohangRepository.save(cartItem);
            }
        }
    }

    //    public List<GiohangResponse> getCartItems(String userId) {
    //        List<Giohang> cartItems = giohangRepository.findByUserId(userId);
    //        return cartItems.stream()
    //                .map(giohang -> GiohangResponse.builder()
    //                        .dienthoaiId(giohang.getDienthoai().getId())
    //                        .mausacId(giohang.getMausac().getId())
    //                        .soluong(giohang.getSoluong())
    //                        .userId(giohang.getUser().getId())
    //                        .build())
    //                .collect(Collectors.toList());
    //    }

    public List<GiohangResponse> getHoadonByUserId(String userId) {
        List<Giohang> giohangs = giohangRepository.findByUser_Id(userId);
        return giohangs.stream().map(giohangMapper::toGiohangResponse).toList();
    }

    public List<GiohangResponse> getGioHangWithDiscount(String userId) {
        List<Giohang> giohangs = giohangRepository.findByUser_Id(userId);
        return giohangs.stream()
                .map(giohang -> {
                    Dienthoai dienthoai = giohang.getDienthoai();
                    String phantramKhuyenmai = khuyenmaiService.getPhanTramKhuyenMai(dienthoai);

                    GiohangResponse response = giohangMapper.toGiohangResponse(giohang);
                    response.setPhantramKhuyenmai(phantramKhuyenmai);

                    return response;
                })
                .toList();
    }
}
