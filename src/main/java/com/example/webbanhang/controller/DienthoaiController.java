package com.example.webbanhang.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.request.SoSanhDienThoaiResponse;
import com.example.webbanhang.dto.response.DienthoaiResponse;
import com.example.webbanhang.dto.response.ThongtinalldienthoaiResponse;
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

    @GetMapping("/random-color")
    public List<Map<String, Object>> getPhoneProductsWithRandomColor() {
        return dienthoaiService.getPhoneProductsWithRandomColor();
    }

    @GetMapping("/filter")
    public List<Map<String, Object>> filterPhoneProducts(
            @RequestParam(required = false) String ram,
            @RequestParam(required = false) String hedieuhanh,
            @RequestParam(required = false) String boNho,
            @RequestParam(required = false) Long giaTu,
            @RequestParam(required = false) Long giaDen,
            @RequestParam(required = false) String tinhnangdacbiet,
            @RequestParam(required = false) String tansoquet,
            @RequestParam(required = false) String kieumanhinh,
            @RequestParam(required = false) String chipset) {

        List<String> ramList = ram != null ? Arrays.asList(ram.split(",")) : null;
        List<String> hedieuhanhList = hedieuhanh != null ? Arrays.asList(hedieuhanh.split(",")) : null;
        List<String> boNhoList = boNho != null ? Arrays.asList(boNho.split(",")) : null;
        List<String> tinhnangdacbietList = tinhnangdacbiet != null ? Arrays.asList(tinhnangdacbiet.split(",")) : null;
        List<String> tansoquetList = tansoquet != null ? Arrays.asList(tansoquet.split(",")) : null;
        List<String> kieumanhinhList = kieumanhinh != null ? Arrays.asList(kieumanhinh.split(",")) : null;
        List<String> chipsetList = chipset != null ? Arrays.asList(chipset.split(",")) : null;

        return dienthoaiService.getPhoneProductsWithFilters(
                ramList,
                hedieuhanhList,
                boNhoList,
                giaTu,
                giaDen,
                tinhnangdacbietList,
                tansoquetList,
                kieumanhinhList,
                chipsetList);
    }

    @GetMapping("/{id}/mausac/{mausacId}")
    public ThongtinalldienthoaiResponse getDienthoaiDetails(@PathVariable Long id, @PathVariable Long mausacId) {
        return dienthoaiService.getDienthoaiDetails(id, mausacId);
    }

    @GetMapping("/so-sanh/{id1},{id2}")
    public SoSanhDienThoaiResponse soSanhDienThoai(@PathVariable Long id1, @PathVariable Long id2) {
        return dienthoaiService.soSanhDienThoai(id1, id2);
    }
}
