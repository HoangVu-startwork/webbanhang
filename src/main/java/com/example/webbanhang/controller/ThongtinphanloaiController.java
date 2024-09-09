package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.ThongtinphanloaiRequest;
import com.example.webbanhang.dto.response.ThongtinphanloaiResponse;
import com.example.webbanhang.service.ThongtinphanloaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/thongtinphanloai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongtinphanloaiController {
    ThongtinphanloaiService thongtinphanloaiService;

    @PostMapping
    public ApiResponse<ThongtinphanloaiResponse> createThongtinphanloai(@RequestBody ThongtinphanloaiRequest request) {
        return ApiResponse.<ThongtinphanloaiResponse>builder()
                .result(thongtinphanloaiService.createThongtinphanloai(request))
                .build();
    }

    @GetMapping("/loaisanpham/{loaisanphamId}")
    public ApiResponse<List<ThongtinphanloaiResponse>> getByThongtinphanloai(@PathVariable Long loaisanphamId) {
        return ApiResponse.<List<ThongtinphanloaiResponse>>builder()
                .result(thongtinphanloaiService.getByThongtinphanloai(loaisanphamId))
                .build();
    }
}
