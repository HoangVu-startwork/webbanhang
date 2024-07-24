package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.KhodienthoaiRequest;
import com.example.webbanhang.dto.response.KhodienthoaiResponse;
import com.example.webbanhang.service.KhodienthoaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/khodienthoai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KhodienthoaiController {
    KhodienthoaiService khodienthoaiService;

    @PostMapping
    public ApiResponse<KhodienthoaiResponse> createKhodienthoai(@RequestBody KhodienthoaiRequest request) {
        return ApiResponse.<KhodienthoaiResponse>builder()
                .result(khodienthoaiService.addToCart(request))
                .build();
    }
}
