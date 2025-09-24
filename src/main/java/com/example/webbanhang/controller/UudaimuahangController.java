package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.UudaimuahangRequest;
import com.example.webbanhang.dto.response.UudaimuahangResponse;
import com.example.webbanhang.service.UudaimuahangService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/uudaimuahang")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UudaimuahangController {

    private final UudaimuahangService uudaimuahangService;

    @PostMapping("/add")
    public UudaimuahangResponse create(@RequestBody UudaimuahangRequest request) {
        return uudaimuahangService.create(request);
    }

    @GetMapping("/all")
    public List<UudaimuahangResponse> getAll() {
        return uudaimuahangService.getAll();
    }

    @GetMapping("/{id}")
    public UudaimuahangResponse getById(@PathVariable Long id) {
        return uudaimuahangService.getById(id);
    }

    @GetMapping("/xephanguser/{xephanguserId}")
    public List<UudaimuahangResponse> getByXephanguser(@PathVariable Long xephanguserId) {
        return uudaimuahangService.getByXephanguser(xephanguserId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        uudaimuahangService.delete(id);
    }
}
