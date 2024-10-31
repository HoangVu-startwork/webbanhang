package com.example.webbanhang.controller;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.ThongsokythuatRequest;
import com.example.webbanhang.dto.request.ThongsokythuatsRequest;
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

    @PostMapping("/dienthoai")
    public ApiResponse<ThongsokythuatResponse> createThongsokythuatId(@RequestBody ThongsokythuatsRequest request) {
        return ApiResponse.<ThongsokythuatResponse>builder()
                .result(thongsokythuatService.createThongsokythuatId(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ThongsokythuatResponse> getDanhmucId(@PathVariable Long id) {
        return ApiResponse.<ThongsokythuatResponse>builder()
                .result(thongsokythuatService.findById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ThongsokythuatResponse> updateThongtindienthoai(
            @PathVariable Long id, @RequestBody ThongsokythuatsRequest request) {
        return ApiResponse.<ThongsokythuatResponse>builder()
                .result(thongsokythuatService.updateThongsokythuat(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    String deleteThongsokythuat(@PathVariable Long id) {
        thongsokythuatService.deleteThongsokythuat(id);
        return "Xoá thành công";
    }
}
