package com.example.webbanhang.service;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.GiohangRequest;
import com.example.webbanhang.dto.request.GiohangsRequest;
import com.example.webbanhang.dto.request.UpdateQuantityRequest;
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

    public GiohangResponse addToCarts(GiohangsRequest giohangRequest) {
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

        // Find user information using userId instead of email
        User user = userRepository
                .findById(giohangRequest.getUserId())
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

    @Transactional // quan trọng: đảm bảo save() thật sự commit
    @Scheduled(fixedRate = 1000, initialDelay = 0)
    // @Transactional:
    // → Đảm bảo toàn bộ method chạy trong một transaction.
    // → Khi bạn gọi giohangRepository.save(...), dữ liệu sẽ được commit vào DB ngay cuối transaction.
    //
    // @Scheduled(fixedRate = 1000, initialDelay = 0):
    // → Đây là Scheduler của Spring.
    // → fixedRate = 1000: cứ 1 giây lại chạy method 1 lần (tính từ lúc bắt đầu chạy).
    // → initialDelay = 0: không chờ, chạy ngay lần đầu khi app khởi động.
    public void updateCartQuantityBasedOnStock() {
        try {
            List<Giohang> cartItems = giohangRepository.findAll();
            if (cartItems.isEmpty()) {
                log.debug("[CartSync] No cart items.");
                return;
            }

            for (Giohang cartItem : cartItems) {
                if (cartItem.getDienthoai() == null || cartItem.getMausac() == null) {
                    log.warn("[CartSync] Missing phone/color for cartItem id={}", cartItem.getId());
                    continue;
                }

                Khodienthoai stock = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
                        cartItem.getDienthoai().getId(), cartItem.getMausac().getId());

                if (stock == null) {
                    log.debug("[CartSync] No stock for cartItem id={}", cartItem.getId());
                    continue;
                }

                Integer availableQuantity = safeToInt(stock.getSoluong());
                Integer cartQuantity = safeToInt(cartItem.getSoluong());

                if (availableQuantity == null || cartQuantity == null) {
                    log.warn(
                            "[CartSync] Non-numeric qty. stock='{}', cart='{}' (id={})",
                            stock.getSoluong(),
                            cartItem.getSoluong(),
                            cartItem.getId());
                    continue;
                }

                String before = cartItem.getSoluong();

                if (availableQuantity == 0) {
                    cartItem.setSoluong("0");
                } else if (cartQuantity == 0 && availableQuantity >= 1) {
                    cartItem.setSoluong("1");
                } else if (availableQuantity < cartQuantity) {
                    cartItem.setSoluong(String.valueOf(availableQuantity));
                } // else: giữ nguyên

                if (!before.equals(cartItem.getSoluong())) {
                    giohangRepository.save(cartItem);
                    log.info(
                            "[CartSync] Updated cartItem id={} from {} -> {} (stock={})",
                            cartItem.getId(),
                            before,
                            cartItem.getSoluong(),
                            availableQuantity);
                }
            }
        } catch (Exception e) {
            log.error("[CartSync] Error while syncing cart quantities", e);
            // Không rethrow để scheduler không bị “chết” chu kỳ tiếp theo
        }
    }

    private Integer safeToInt(String s) {
        try {
            if (s == null) return null;
            s = s.trim();
            if (s.isEmpty()) return null;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    //    Nhận vào chuỗi s.
    // Nếu null hoặc rỗng → trả về null.
    // Nếu parse được → trả về số nguyên.
    // Nếu parse lỗi → trả về null.
    // Mục đích: giúp tránh crash khi dữ liệu trong DB là chuỗi rác.

    //    @Scheduled(fixedRate = 1000)
    //    public void updateCartQuantityBasedOnStock() {
    //        List<Giohang> cartItems = giohangRepository.findAll();
    //        for (Giohang cartItem : cartItems) {
    //            Khodienthoai stock = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
    //                    cartItem.getDienthoai().getId(), cartItem.getMausac().getId());
    //
    //            if (stock != null) {
    //                int availableQuantity = Integer.parseInt(stock.getSoluong());
    //                int cartQuantity = Integer.parseInt(cartItem.getSoluong());
    //
    //                if (availableQuantity == 0) {
    //                    // Trường hợp kho hết hàng → giỏ hàng = 0
    //                    cartItem.setSoluong("0");
    //
    //                } else if (cartQuantity == 0 && availableQuantity >= 1) {
    //                    // Nếu giỏ hàng đang = 0 nhưng kho còn hàng thì set lại = 1
    //                    cartItem.setSoluong("1");
    //
    //                } else if (availableQuantity < cartQuantity) {
    //                    // Nếu giỏ hàng nhiều hơn số lượng tồn thì giảm về tồn
    //                    cartItem.setSoluong(String.valueOf(availableQuantity));
    //                }
    //
    //                giohangRepository.save(cartItem);
    //            }
    //        }
    //    }

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

    @Transactional
    public GiohangResponse updateQuantity(UpdateQuantityRequest req) {
        Giohang cart = giohangRepository
                .findById(req.getGiohangId())
                .orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI));

        // Lấy tồn kho theo (dienthoai, mausac)
        Khodienthoai stock = khodienthoaiRepository.findByDienthoaiIdAndMausacId(
                cart.getDienthoai().getId(), cart.getMausac().getId());

        if (stock == null) {
            throw new AppException(ErrorCode.KHOHANG); // không có bản ghi kho
        }

        int available = Integer.parseInt(stock.getSoluong());
        if (available <= 0) {
            throw new AppException(ErrorCode.KHOHANG); // hết hàng
        }

        // clamp số lượng trong [1..available]
        int desired = Math.max(1, Math.min(req.getQuantity(), available));

        cart.setSoluong(String.valueOf(desired));
        Giohang saved = giohangRepository.save(cart);

        GiohangResponse res = giohangMapper.toGiohangResponse(saved);
        // (nếu cần %) khuyến mại
        String phantram = khuyenmaiService.getPhanTramKhuyenMai(saved.getDienthoai());
        res.setPhantramKhuyenmai(phantram);
        return res;
    }
}
