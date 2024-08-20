package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.GiohangRequest;
import com.example.webbanhang.dto.response.GiohangResponse;
import com.example.webbanhang.service.GiohangService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/giohang")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiohangController {
    GiohangService giohangService;

    @PostMapping
    public ApiResponse<GiohangResponse> createKhodienthoai(@RequestBody GiohangRequest request) {
        return ApiResponse.<GiohangResponse>builder()
                .result(giohangService.addToCart(request))
                .build();
    }

    @DeleteMapping("/{giohangId}")
    public ApiResponse<Void> removeFromCart(@PathVariable Long giohangId) {
        giohangService.removeFromCart(giohangId);
        return ApiResponse.<Void>builder().result(null).build();
    }

    @PostMapping("/update-cart-quantity")
    public void updateCartQuantityBasedOnStock() {
        giohangService.updateCartQuantityBasedOnStock();
    }

    @GetMapping("user/{userId}")
    ApiResponse<List<GiohangResponse>> getHoadonByUserId(@PathVariable String userId) {
        return ApiResponse.<List<GiohangResponse>>builder()
                .result(giohangService.getGioHangWithDiscount(userId))
                .build();
    }
}
