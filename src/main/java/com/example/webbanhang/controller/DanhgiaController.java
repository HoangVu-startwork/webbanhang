package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.DanhgiaRequest;
import com.example.webbanhang.dto.response.DanhgiaResponse;
import com.example.webbanhang.dto.response.DanhgiasaoResponse;
import com.example.webbanhang.dto.response.DanhgiatongsaoResponse;
import com.example.webbanhang.service.DanhgiaService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/danhgia")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DanhgiaController {

    DanhgiaService danhgiaService;

    @PostMapping
    public ApiResponse<DanhgiaResponse> addComment(@RequestBody DanhgiaRequest request) {
        return ApiResponse.<DanhgiaResponse>builder()
                .result(danhgiaService.createDanhgia(request))
                .build();
    }

    @GetMapping("/{dienthoaiId}")
    public ApiResponse<List<DanhgiaResponse>> getDanhmucId(@PathVariable Long dienthoaiId) {
        return ApiResponse.<List<DanhgiaResponse>>builder()
                .result(danhgiaService.getCommentsByDienthoaiId(dienthoaiId))
                .build();
    }

    @GetMapping("tongsao/{dienthoaiId}")
    public ApiResponse<DanhgiasaoResponse> getTongsao(@PathVariable Long dienthoaiId) {
        return ApiResponse.<DanhgiasaoResponse>builder()
                .result(danhgiaService.getDanhgiasa(dienthoaiId))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<DanhgiatongsaoResponse>> getCommentsAll() {
        return ApiResponse.<List<DanhgiatongsaoResponse>>builder()
                .result(danhgiaService.getAllTongsaoForAllPhones())
                .build();
    }
}
