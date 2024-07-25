package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.KhuyenmaiRequest;
import com.example.webbanhang.dto.response.KhuyenmaiResponse;
import com.example.webbanhang.service.KhuyenmaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/khuyenmai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KhuyenmaiController {
    KhuyenmaiService khuyenmaiService;

    @PostMapping
    public ApiResponse<KhuyenmaiResponse> createHoadon(@RequestBody KhuyenmaiRequest request) {
        KhuyenmaiResponse response = khuyenmaiService.saveKhuyenmai(request);
        return ApiResponse.<KhuyenmaiResponse>builder().result(response).build();
    }
}
