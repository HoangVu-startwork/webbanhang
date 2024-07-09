package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.LoaisanphamRequest;
import com.example.webbanhang.dto.response.LoaisanphamResponse;
import com.example.webbanhang.service.LoaisanphamService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/loaisanpham")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoaisanphamController {
    LoaisanphamService loaisanphamService;

    @PostMapping
    public ApiResponse<LoaisanphamResponse> createLoaisanpham(@RequestBody LoaisanphamRequest request) {
        return ApiResponse.<LoaisanphamResponse>builder()
                .result(loaisanphamService.createLoaisanpham(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<LoaisanphamResponse> updateLoaisanpham(
            @PathVariable Long id, @RequestBody LoaisanphamRequest request) {
        return ApiResponse.<LoaisanphamResponse>builder()
                .result(loaisanphamService.updateDanhmuc(id, request))
                .build();
    }
}
