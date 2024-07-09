package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.DanhmucRequest;
import com.example.webbanhang.dto.response.DanhmucResponse;
import com.example.webbanhang.service.DanhmucService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/danhmuc")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DanhmucController {
    DanhmucService danhmucService;

    @PostMapping
    public ApiResponse<DanhmucResponse> createDanhmuc(@RequestBody DanhmucRequest request) {
        return ApiResponse.<DanhmucResponse>builder()
                .result(danhmucService.createDanhmuc(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DanhmucResponse> updateDanhmuc(@PathVariable Long id, @RequestBody DanhmucRequest request) {
        return ApiResponse.<DanhmucResponse>builder()
                .result(danhmucService.updateDanhmuc(id, request))
                .build();
    }
}
