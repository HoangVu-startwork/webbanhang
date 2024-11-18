package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.HedieuhanhRequest;
import com.example.webbanhang.dto.response.HedieuhanhResponse;
import com.example.webbanhang.service.HedieuhanhService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hedieuhanh")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HedieuhanhController {
    HedieuhanhService hedieuhanhService;

    @PostMapping
    ApiResponse<HedieuhanhResponse> create(@RequestBody HedieuhanhRequest request) {
        return ApiResponse.<HedieuhanhResponse>builder()
                .result(hedieuhanhService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<HedieuhanhResponse> updateHedieuhanh(
            @PathVariable Long id, @RequestBody HedieuhanhRequest request) {
        return ApiResponse.<HedieuhanhResponse>builder()
                .result(hedieuhanhService.updateHedieuhanh(id, request))
                .build();
    }

    @GetMapping
    ApiResponse<List<HedieuhanhResponse>> getAllHedieuhanh() {
        return ApiResponse.<List<HedieuhanhResponse>>builder()
                .result(hedieuhanhService.findAllhedieuhanh())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<HedieuhanhResponse> getHedieuhanhId(@PathVariable Long id) {
        return ApiResponse.<HedieuhanhResponse>builder()
                .result(hedieuhanhService.getHedieuhanhById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    String deleteHedieuhanh(@PathVariable Long id) {
        hedieuhanhService.deleteHedieuhanh(id);
        return "User has been deleted";
    }
}
