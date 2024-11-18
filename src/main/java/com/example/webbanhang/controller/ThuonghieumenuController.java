package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.ThuonghieumenuRequest;
import com.example.webbanhang.dto.response.ThuonghieumenuResponse;
import com.example.webbanhang.dto.response.ThuonghieumenusResponse;
import com.example.webbanhang.service.ThuonghieumenuService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/thuonghieumenu")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThuonghieumenuController {
    ThuonghieumenuService thuonghieumenuService;

    @GetMapping("/all")
    public List<ThuonghieumenuResponse> getAllThuonghieumenu() {
        return thuonghieumenuService.getAllThuonghieumenu();
    }

    @GetMapping("/all-admin")
    public List<ThuonghieumenusResponse> getAllThuonghieumenuAD() {
        return thuonghieumenuService.getAllThuonghieumenuAd();
    }

    @GetMapping("/{id}")
    ApiResponse<ThuonghieumenusResponse> getIdThuonghieumenudienthoai(@PathVariable Long id) {
        return ApiResponse.<ThuonghieumenusResponse>builder()
                .result(thuonghieumenuService.findThuonghieumenudienthoai(id))
                .build();
    }

    @PostMapping("/add")
    public ThuonghieumenuResponse addThuonghieumenu(@RequestBody ThuonghieumenuRequest request) {
        return thuonghieumenuService.addThuonghieumenu(request);
    }

    @PutMapping("/update/{id}")
    public ThuonghieumenuResponse updateThuonghieumenu(
            @PathVariable Long id, @RequestBody ThuonghieumenuRequest request) {
        return thuonghieumenuService.updateThuonghieumenu(id, request);
    }

    @DeleteMapping("/{id}")
    String deleteThuonghieumenu(@PathVariable Long id) {
        thuonghieumenuService.deletethuonghieumenudienthoai(id);
        return "Xoá thương hiệu menu thành công";
    }
}
