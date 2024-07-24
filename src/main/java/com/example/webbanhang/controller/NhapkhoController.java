package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.NhapkhoRequest;
import com.example.webbanhang.dto.response.NhapkhoResponse;
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
    public ApiResponse<NhapkhoResponse> createNhapkho(@RequestBody NhapkhoRequest request) {
        return ApiResponse.<NhapkhoResponse>builder()
                .result(nhapkhoService.nhapKho(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NhapkhoResponse> updateNhapkho(@PathVariable Long id, @RequestBody NhapkhoRequest request) {
        return ApiResponse.<NhapkhoResponse>builder()
                .result(nhapkhoService.updateNhapkho(id, request))
                .build();
    }
}
