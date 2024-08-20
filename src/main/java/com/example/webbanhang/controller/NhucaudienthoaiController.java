package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.NhucaudienthoaiRequest;
import com.example.webbanhang.dto.response.NhucaudienthoaiResponse;
import com.example.webbanhang.service.NhucaudienthoaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/nhucaudienthoai")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NhucaudienthoaiController {

    NhucaudienthoaiService nhucaudienthoaiService;

    @PostMapping
    public ApiResponse<NhucaudienthoaiResponse> addComment(@RequestBody NhucaudienthoaiRequest request) {
        return ApiResponse.<NhucaudienthoaiResponse>builder()
                .result(nhucaudienthoaiService.createNhucaudienthoai(request))
                .build();
    }
}
