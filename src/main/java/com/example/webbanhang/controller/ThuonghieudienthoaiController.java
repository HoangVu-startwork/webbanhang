package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.ThuonghieudienthoaiRequest;
import com.example.webbanhang.dto.response.ThuonghieudienthoaiResponse;
import com.example.webbanhang.dto.response.ThuonghieudienthoaisResponse;
import com.example.webbanhang.service.ThuonghieudienthoaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/thuonghieu")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThuonghieudienthoaiController {
    ThuonghieudienthoaiService thuonghieudienthoaiService;

    @GetMapping("/all")
    public List<ThuonghieudienthoaisResponse> getAllThuonghieudienthoai() {
        return thuonghieudienthoaiService.getAllThuonghieudienthoai();
    }

    @GetMapping("/all-thuonghieu")
    public List<ThuonghieudienthoaisResponse> getAllThuonghieudienthoaiweb() {
        return thuonghieudienthoaiService.getAllThuonghieudienthoaiweb();
    }

    @PostMapping("/add")
    public ThuonghieudienthoaiResponse addThuonghieudienthoai(@RequestBody ThuonghieudienthoaiRequest request) {
        return thuonghieudienthoaiService.addThuonghieudienthoai(request);
    }

    @PutMapping("/update/{id}")
    public ThuonghieudienthoaiResponse updateThuonghieudienthoai(
            @PathVariable Long id, @RequestBody ThuonghieudienthoaiRequest request) {
        return thuonghieudienthoaiService.updateThuonghieudienthoai(id, request);
    }

    @GetMapping("/{id}")
    ApiResponse<ThuonghieudienthoaisResponse> getIdThuonghieudienthoai(@PathVariable Long id) {
        return ApiResponse.<ThuonghieudienthoaisResponse>builder()
                .result(thuonghieudienthoaiService.findThuonghieudienthoai(id))
                .build();
    }

    @DeleteMapping("/{id}")
    String deleteThuonghieudienthoai(@PathVariable Long id) {
        thuonghieudienthoaiService.deletethuonghieudienthoai(id);
        return "Xoá thương hiệu điện thoại thành công";
    }
}
