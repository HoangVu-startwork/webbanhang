package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.NhapkhosRequest;
import com.example.webbanhang.dto.response.NhapkhosResponse;
import com.example.webbanhang.service.NhapkhoService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/nhapkho")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NhapkhoController {
    NhapkhoService nhapkhoService;

    @PostMapping
    public ApiResponse<NhapkhosResponse> createNhapkho(@RequestBody NhapkhosRequest request) {
        return ApiResponse.<NhapkhosResponse>builder()
                .result(nhapkhoService.nhapKho(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NhapkhosResponse> updateNhapkho(@PathVariable Long id, @RequestBody NhapkhosRequest request) {
        return ApiResponse.<NhapkhosResponse>builder()
                .result(nhapkhoService.updateNhapkho(id, request))
                .build();
    }

    @GetMapping
    ApiResponse<List<NhapkhosResponse>> getAllNhapkho() {
        return ApiResponse.<List<NhapkhosResponse>>builder()
                .result(nhapkhoService.findAllNhapkho())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<NhapkhosResponse> getHedieuhanhId(@PathVariable Long id) {
        return ApiResponse.<NhapkhosResponse>builder()
                .result(nhapkhoService.getNhapkho(id))
                .build();
    }
}
