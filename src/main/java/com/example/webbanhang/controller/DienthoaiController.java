package com.example.webbanhang.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.*;
import com.example.webbanhang.dto.response.*;
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
            @PathVariable Long id, @RequestBody DienthoaisRequest request) {
        return ApiResponse.<DienthoaiResponse>builder()
                .result(dienthoaiService.updateDienthoai(id, request))
                .build();
    }

    @GetMapping("/random-color")
    public List<Map<String, Object>> getPhoneProductsWithRandomColor() {
        return dienthoaiService.getPhoneProductsWithRandomColor();
    }

    @GetMapping("/all")
    public ApiResponse<List<DienthoaihethongResponse>> getDanhmuc() {
        List<DienthoaihethongResponse> phoneProducts = dienthoaiService.getAllPhoneWithColors();
        return ApiResponse.<List<DienthoaihethongResponse>>builder()
                .result(phoneProducts) // Set kết quả từ service
                .build();
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
            @RequestParam(required = false) String thietbidikem,
            @RequestParam(required = false) String chipset) {

        List<String> ramList = ram != null && !ram.isEmpty() ? Arrays.asList(ram.split(",")) : null;
        List<String> hedieuhanhList =
                hedieuhanh != null && !hedieuhanh.isEmpty() ? Arrays.asList(hedieuhanh.split(",")) : null;
        List<String> boNhoList = boNho != null && !boNho.isEmpty() ? Arrays.asList(boNho.split(",")) : null;
        List<String> tinhnangdacbietList = tinhnangdacbiet != null && !tinhnangdacbiet.isEmpty()
                ? Arrays.asList(tinhnangdacbiet.split(","))
                : null;
        List<String> kichthuocmanhinhList = kichthuocmanhinh != null && !kichthuocmanhinh.isEmpty()
                ? Arrays.asList(kichthuocmanhinh.split(","))
                : null;
        List<String> tinhnagcameraList =
                tinhnagcamera != null && !tinhnagcamera.isEmpty() ? Arrays.asList(tinhnagcamera.split(",")) : null;
        List<String> tansoquetList =
                tansoquet != null && !tansoquet.isEmpty() ? Arrays.asList(tansoquet.split(",")) : null;
        List<String> kieumanhinhList =
                kieumanhinh != null && !kieumanhinh.isEmpty() ? Arrays.asList(kieumanhinh.split(",")) : null;
        List<String> thietbidikemList =
                thietbidikem != null && !thietbidikem.isEmpty() ? Arrays.asList(thietbidikem.split(",")) : null;
        List<String> chipsetList = chipset != null && !chipset.isEmpty() ? Arrays.asList(chipset.split(",")) : null;

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
    public ApiResponse<List<DienthoaihethongResponse>> getDienthoaiByThongtinphanloai(
            @PathVariable Long thongtinphanloaiId) {
        List<DienthoaihethongResponse> dienthoais = dienthoaiService.getThongtinphanloai(thongtinphanloaiId);
        return ApiResponse.<List<DienthoaihethongResponse>>builder()
                .result(dienthoais)
                .build();
    }

    @GetMapping("/thongtinphanloai98/{id}")
    public ThongtinphanloaidienthoaiResponse getThongtinphanloaiByDienthoaiId(@PathVariable Long id) {
        return dienthoaiService.getThongtinphanloaiByDienthoaiId(id);
    }

    //

    @GetMapping("/dienthoai-filter")
    public List<Map<String, Object>> filterPhoneProducts1(
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
            @RequestParam(required = false) String thietbidikem,
            @RequestParam(required = false) String tinhtrang,
            @RequestParam(required = false) String tensanpham,
            @RequestParam(required = false) String chipset) {

        List<String> ramList = ram != null && !ram.isEmpty() ? Arrays.asList(ram.split(",")) : null;
        List<String> hedieuhanhList =
                hedieuhanh != null && !hedieuhanh.isEmpty() ? Arrays.asList(hedieuhanh.split(",")) : null;
        List<String> boNhoList = boNho != null && !boNho.isEmpty() ? Arrays.asList(boNho.split(",")) : null;
        List<String> tinhnangdacbietList = tinhnangdacbiet != null && !tinhnangdacbiet.isEmpty()
                ? Arrays.asList(tinhnangdacbiet.split(","))
                : null;
        List<String> kichthuocmanhinhList = kichthuocmanhinh != null && !kichthuocmanhinh.isEmpty()
                ? Arrays.asList(kichthuocmanhinh.split(","))
                : null;
        List<String> tinhnagcameraList =
                tinhnagcamera != null && !tinhnagcamera.isEmpty() ? Arrays.asList(tinhnagcamera.split(",")) : null;
        List<String> tansoquetList =
                tansoquet != null && !tansoquet.isEmpty() ? Arrays.asList(tansoquet.split(",")) : null;
        List<String> kieumanhinhList =
                kieumanhinh != null && !kieumanhinh.isEmpty() ? Arrays.asList(kieumanhinh.split(",")) : null;
        List<String> thietbidikemList =
                thietbidikem != null && !thietbidikem.isEmpty() ? Arrays.asList(thietbidikem.split(",")) : null;
        List<String> tinhtrangList =
                tinhtrang != null && !tinhtrang.isEmpty() ? Arrays.asList(tinhtrang.split(",")) : null;
        List<String> chipsetList = chipset != null && !chipset.isEmpty() ? Arrays.asList(chipset.split(",")) : null;

        return dienthoaiService.getPhoneProductsWithFilters1(
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
                thietbidikemList,
                tinhtrangList,
                tensanpham,
                chipsetList);
    }

    @GetMapping("/{id}")
    public ApiResponse<DienthoaiResponse> getDienthoaiId(@PathVariable Long id) {
        return ApiResponse.<DienthoaiResponse>builder()
                .result(dienthoaiService.getDienthoaiById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<DienthoaiResponse>> getAllDienthoai() {
        return ApiResponse.<List<DienthoaiResponse>>builder()
                .result(dienthoaiService.findAlldienthoai())
                .build();
    }

    @GetMapping("/kiemtra/{dienthoaiId}/mausac/{mausacId}")
    public ApiResponse<String> checkDienthoaiMausac(@PathVariable Long dienthoaiId, @PathVariable Long mausacId) {
        return ApiResponse.<String>builder()
                .result(dienthoaiService.checkDienthoaiMausac(dienthoaiId, mausacId))
                .build();
    }

    @DeleteMapping("/{id}")
    String deleteDienthoai(@PathVariable Long id) {
        dienthoaiService.deleteDienthoai(id);
        return "Xoá điện thoại thành công";
    }
}
