package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.HoadonRequest;
import com.example.webbanhang.dto.response.HoadonResponse;
import com.example.webbanhang.service.HoadonService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hoadon")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoadonController {
    private final HoadonService hoadonService;

    @PostMapping
    public ApiResponse<HoadonResponse> createHoadon(@RequestBody HoadonRequest request) {
        HoadonResponse response = hoadonService.createHoadon(request);
        return ApiResponse.<HoadonResponse>builder().result(response).build();
    }
}
