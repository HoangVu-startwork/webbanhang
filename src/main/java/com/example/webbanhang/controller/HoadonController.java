package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.HoadonRequest;
import com.example.webbanhang.dto.response.HoadonResponse;
import com.example.webbanhang.mapper.HoadonMapper;
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
    HoadonMapper hoadonMapper;

    @PostMapping
    public ApiResponse<HoadonResponse> createHoadon(@RequestBody HoadonRequest request) {
        HoadonResponse response = hoadonService.createHoadon(request);
        return ApiResponse.<HoadonResponse>builder().result(response).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<HoadonResponse> getHoadonById(@PathVariable Long id) {
        return ApiResponse.<HoadonResponse>builder()
                .result(hoadonService.getHoadonById(id))
                .build();
    }

    @GetMapping("user/{userId}")
    ApiResponse<List<HoadonResponse>> getHoadonByUserId(@PathVariable String userId) {
        return ApiResponse.<List<HoadonResponse>>builder()
                .result(hoadonService.getHoadonByUserId(userId))
                .build();
    }
}
