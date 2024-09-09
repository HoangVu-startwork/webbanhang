package com.example.webbanhang.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.request.DienthoaihethongRequest;
import com.example.webbanhang.dto.request.SoSanhDienThoaiResponse;
import com.example.webbanhang.dto.response.DienthoaiResponse;
import com.example.webbanhang.dto.response.ThongtinalldienthoaiResponse;
import com.example.webbanhang.dto.response.ThongtinphanloaidienthoaiResponse;
import com.example.webbanhang.repository.DienthoaiRepository;
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
    DienthoaiRepository dienthoaiRepository;

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
            @RequestParam(required = false) String kichthuocmanhinh,
            @RequestParam(required = false) String tinhnagcamera,
            @RequestParam(required = false) String tansoquet,
            @RequestParam(required = false) String kieumanhinh,
            @RequestParam(required = false) String tinhtrangmay,
            @RequestParam(required = false) String thietbidikem,
            @RequestParam(required = false) String chipset) {

        List<String> ramList = ram != null ? Arrays.asList(ram.split(",")) : null;
        List<String> hedieuhanhList = hedieuhanh != null ? Arrays.asList(hedieuhanh.split(",")) : null;
        List<String> boNhoList = boNho != null ? Arrays.asList(boNho.split(",")) : null;
        List<String> tinhnangdacbietList = tinhnangdacbiet != null ? Arrays.asList(tinhnangdacbiet.split(",")) : null;
        List<String> kichthuocmanhinhList =
                kichthuocmanhinh != null ? Arrays.asList(kichthuocmanhinh.split(",")) : null;
        List<String> tinhnagcameraList = tinhnagcamera != null ? Arrays.asList(tinhnagcamera.split(",")) : null;
        List<String> tansoquetList = tansoquet != null ? Arrays.asList(tansoquet.split(",")) : null;
        List<String> kieumanhinhList = kieumanhinh != null ? Arrays.asList(kieumanhinh.split(",")) : null;
        List<String> tinhtrangmayList = tinhtrangmay != null ? Arrays.asList(tinhtrangmay.split(",")) : null;
        List<String> thietbidikemList = thietbidikem != null ? Arrays.asList(thietbidikem.split(",")) : null;
        List<String> chipsetList = chipset != null ? Arrays.asList(chipset.split(",")) : null;

        return dienthoaiService.getPhoneProductsWithFilters(
                ramList,
                hedieuhanhList,
                boNhoList,
                giaTu,
                giaDen,
                tinhnangdacbietList,
                kichthuocmanhinhList,
                tinhnagcameraList,
                tansoquetList,
                kieumanhinhList,
                tinhtrangmayList,
                thietbidikemList,
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

    //    @GetMapping("/hedieuhanh/{hedieuhanhId}")
    //    public List<Dienthoai> getDienthoaiByHedieuhanh(@PathVariable Long hedieuhanhId) {
    //        return dienthoaiRepository.findByHedieuhanhId(hedieuhanhId);
    //    }

    @GetMapping("/hedieuhanh/{hedieuhanhId}")
    public ApiResponse<List<DienthoaihethongRequest>> getDienthoaiByHedieuhanh(@PathVariable Long hedieuhanhId) {
        List<DienthoaihethongRequest> dienthoais = dienthoaiService.getDienthoaiByHedieuhanhId(hedieuhanhId);
        return ApiResponse.<List<DienthoaihethongRequest>>builder()
                .result(dienthoais)
                .build();
    }

    @GetMapping("/loaisanpham/{loaisanphamId}")
    public ApiResponse<List<DienthoaihethongRequest>> getDienthoaiByLoaisanpham(@PathVariable Long loaisanphamId) {
        List<DienthoaihethongRequest> dienthoais = dienthoaiService.getLoaisanphamId(loaisanphamId);
        return ApiResponse.<List<DienthoaihethongRequest>>builder()
                .result(dienthoais)
                .build();
    }

    @GetMapping("/danhmuc/{danhmucId}")
    public ApiResponse<List<DienthoaihethongRequest>> getDienthoaiByDanhmuc(@PathVariable Long danhmucId) {
        List<DienthoaihethongRequest> dienthoais = dienthoaiService.getDanhmuc(danhmucId);
        return ApiResponse.<List<DienthoaihethongRequest>>builder()
                .result(dienthoais)
                .build();
    }

    @GetMapping("/thongtinphanloai/{thongtinphanloaiId}")
    public ApiResponse<List<DienthoaihethongRequest>> getDienthoaiByThongtinphanloai(
            @PathVariable Long thongtinphanloaiId) {
        List<DienthoaihethongRequest> dienthoais = dienthoaiService.getThongtinphanloai(thongtinphanloaiId);
        return ApiResponse.<List<DienthoaihethongRequest>>builder()
                .result(dienthoais)
                .build();
    }

    @GetMapping("/thongtinphanloai98/{id}")
    public ThongtinphanloaidienthoaiResponse getThongtinphanloaiByDienthoaiId(@PathVariable Long id) {
        return dienthoaiService.getThongtinphanloaiByDienthoaiId(id);
    }
}
