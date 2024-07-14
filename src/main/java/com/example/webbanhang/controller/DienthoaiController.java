package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.response.DienthoaiResponse;
import com.example.webbanhang.service.DienthoaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/dienthoai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DienthoaiController {
    DienthoaiService dienthoaiService;

    @PostMapping
    public ApiResponse<DienthoaiResponse> createDienthoai(@RequestBody DienthoaiRequest request) {
        return ApiResponse.<DienthoaiResponse>builder()
                .result(dienthoaiService.createDienthoai(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DienthoaiResponse> updateDienthoai(
            @PathVariable Long id, @RequestBody DienthoaiRequest request) {
        return ApiResponse.<DienthoaiResponse>builder()
                .result(dienthoaiService.updateDienthoai(id, request))
                .build();
    }
}
