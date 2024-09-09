package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.KetnoinhucauRequest;
import com.example.webbanhang.dto.request.NhucaudienthoaiRequest;
import com.example.webbanhang.dto.response.KetnoinhucauResponse;
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

    @PostMapping("/add")
    public ApiResponse<KetnoinhucauResponse> addComment(@RequestBody KetnoinhucauRequest request) {
        return ApiResponse.<KetnoinhucauResponse>builder()
                .result(nhucaudienthoaiService.addKetnoinhucau(request))
                .build();
    }

    @GetMapping("ketnoinhucau/{id}")
    ApiResponse<KetnoinhucauResponse> getketnoinhucauId(@PathVariable Long id) {
        return ApiResponse.<KetnoinhucauResponse>builder()
                .result(nhucaudienthoaiService.getKetnoinhucau(id))
                .build();
    }

    @GetMapping("nhucaudienthoai/{id}")
    ApiResponse<NhucaudienthoaiResponse> getnhucaudienthoaiId(@PathVariable Long id) {
        return ApiResponse.<NhucaudienthoaiResponse>builder()
                .result(nhucaudienthoaiService.getNhucaudienthoai(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<NhucaudienthoaiResponse>> getAllNhucaudienthoai() {
        return ApiResponse.<List<NhucaudienthoaiResponse>>builder()
                .result(nhucaudienthoaiService.getAllNhucaudienthoai())
                .build();
    }
}
