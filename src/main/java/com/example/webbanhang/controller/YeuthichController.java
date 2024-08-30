package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.YeuthichRequest;
import com.example.webbanhang.dto.response.YeuthichResponse;
import com.example.webbanhang.service.YeuthichService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/yeuthich")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class YeuthichController {
    YeuthichService yeuthichService;

    @PostMapping
    public ApiResponse<YeuthichResponse> addYeuthich(@RequestBody YeuthichRequest request) {
        return ApiResponse.<YeuthichResponse>builder()
                .result(yeuthichService.addYeuthich(request))
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<YeuthichResponse>> getDanhmucId(@PathVariable String userId) {
        return ApiResponse.<List<YeuthichResponse>>builder()
                .result(yeuthichService.getYeuthichByUserId(userId))
                .build();
    }
}
