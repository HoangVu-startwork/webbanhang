package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.ThongtindienthoaiRequest;
import com.example.webbanhang.dto.response.ThongtindienthoaiResponse;
import com.example.webbanhang.service.ThongtindienthoaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/thongtindienthoai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongtindienthoaiController {
    ThongtindienthoaiService thongtindienthoaiService;

    @PostMapping
    public ApiResponse<ThongtindienthoaiResponse> createThongtindienthoai(
            @RequestBody ThongtindienthoaiRequest request) {
        return ApiResponse.<ThongtindienthoaiResponse>builder()
                .result(thongtindienthoaiService.createThongtindienthoai(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ThongtindienthoaiResponse> updateThongtindienthoai(
            @PathVariable Long id, @RequestBody ThongtindienthoaiRequest request) {
        return ApiResponse.<ThongtindienthoaiResponse>builder()
                .result(thongtindienthoaiService.updateThongtindienthoai(id, request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ThongtindienthoaiResponse> getThongtindienthoaiId(@PathVariable Long id) {
        return ApiResponse.<ThongtindienthoaiResponse>builder()
                .result(thongtindienthoaiService.findThongtindienthoai(id))
                .build();
    }
}
