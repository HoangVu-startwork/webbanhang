package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.MausacRequest;
import com.example.webbanhang.dto.response.MausacResponse;
import com.example.webbanhang.service.MausacService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mausac")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MausacController {
    MausacService mausacService;

    @PostMapping("/maudienthoai")
    public ApiResponse<MausacResponse> createMausac(@RequestBody MausacRequest request) {
        return ApiResponse.<MausacResponse>builder()
                .result(mausacService.createMausac(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MausacResponse> updateMausac(@PathVariable Long id, @RequestBody MausacRequest request) {
        return ApiResponse.<MausacResponse>builder()
                .result(mausacService.updateMausac(id, request))
                .build();
    }
}
