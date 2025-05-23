package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.MausacsRequest;
import com.example.webbanhang.dto.response.MausacResponse;
import com.example.webbanhang.service.MausacService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mausac")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MausacController {
    MausacService mausacService;

    @PostMapping("/maudienthoai")
    public ApiResponse<MausacResponse> createMausac(@RequestBody MausacsRequest request) {
        return ApiResponse.<MausacResponse>builder()
                .result(mausacService.createMausac(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MausacResponse> updateMausac(@PathVariable Long id, @RequestBody MausacsRequest request) {
        return ApiResponse.<MausacResponse>builder()
                .result(mausacService.updateMausac(id, request))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<MausacResponse>> getMausac() {
        return ApiResponse.<List<MausacResponse>>builder()
                .result(mausacService.getAllMausac())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<MausacResponse> getmausacId(@PathVariable Long id) {
        return ApiResponse.<MausacResponse>builder()
                .result(mausacService.getmausacId(id))
                .build();
    }

    @DeleteMapping("/{id}")
    String deleteMausac(@PathVariable Long id) {
        mausacService.deleteMausac(id);
        return "Xoá thành công";
    }

    @GetMapping("/dienthoai/{dienthoaiId}")
    public ApiResponse<List<MausacResponse>> getMausacsByDienthoaiId(@PathVariable Long dienthoaiId) {
        return ApiResponse.<List<MausacResponse>>builder()
                .result(mausacService.findByDienthoaiId(dienthoaiId))
                .build();
    }

    @DeleteMapping("/dienthoai/{dienthoaiId}")
    public ApiResponse<String> deleteMausacByDienthoaiId(@PathVariable Long dienthoaiId) {
        String result = mausacService.deleteByDienthoaiId(dienthoaiId);
        return ApiResponse.<String>builder().result(result).build();
    }
}
