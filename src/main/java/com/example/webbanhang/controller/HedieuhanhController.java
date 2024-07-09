package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.HedieuhanhRequest;
import com.example.webbanhang.dto.response.HedieuhanhResponse;
import com.example.webbanhang.service.HedieuhanhService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hedieuhanh")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HedieuhanhController {
    HedieuhanhService hedieuhanhService;

    @PostMapping
    ApiResponse<HedieuhanhResponse> create(@RequestBody HedieuhanhRequest request) {
        return ApiResponse.<HedieuhanhResponse>builder()
                .result(hedieuhanhService.create(request))
                .build();
    }
}
