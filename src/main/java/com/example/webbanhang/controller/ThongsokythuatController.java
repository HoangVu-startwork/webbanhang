package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.ThongsokythuatRequest;
import com.example.webbanhang.dto.response.ThongsokythuatResponse;
import com.example.webbanhang.service.ThongsokythuatService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/thongsokythuat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongsokythuatController {
    ThongsokythuatService thongsokythuatService;

    @PostMapping
    public ApiResponse<ThongsokythuatResponse> createThongsokythuat(@RequestBody ThongsokythuatRequest request) {
        return ApiResponse.<ThongsokythuatResponse>builder()
                .result(thongsokythuatService.createThongsokythuat(request))
                .build();
    }
}
