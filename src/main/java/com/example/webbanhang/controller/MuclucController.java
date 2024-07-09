package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
