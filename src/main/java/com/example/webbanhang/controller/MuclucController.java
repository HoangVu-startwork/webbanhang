package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.MuclucRequest;
import com.example.webbanhang.dto.response.MuclucResponse;
import com.example.webbanhang.service.MuclucService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mucluc")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MuclucController {
    MuclucService muclucService;

    @PostMapping
    ApiResponse<MuclucResponse> create(@RequestBody MuclucRequest request) {
        return ApiResponse.<MuclucResponse>builder()
                .result(muclucService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<MuclucResponse>> getAllDanhmuc() {
        return ApiResponse.<List<MuclucResponse>>builder()
                .result(muclucService.getAllDanhmuc())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<MuclucResponse> getByTenmucluc(@PathVariable Long id) {
        return ApiResponse.<MuclucResponse>builder()
                .result(muclucService.getByTenmucluc(id))
                .build();
    }

    @DeleteMapping("/{id}")
    String deleteMucluc(@PathVariable Long id) {
        muclucService.deleteMucluc(id);
        return "User has been deleted";
    }
}
