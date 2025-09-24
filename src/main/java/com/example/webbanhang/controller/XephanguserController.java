package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.XephanguserRequest;
import com.example.webbanhang.dto.response.XephanguserResponse;
import com.example.webbanhang.service.XephanguserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/xephanguser")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class XephanguserController {

    private final XephanguserService xephanguserService;

    @GetMapping("/{id}")
    public XephanguserResponse getById(@PathVariable Long id) {
        return xephanguserService.getById(id);
    }

    @GetMapping("/all")
    public List<XephanguserResponse> getAll() {
        return xephanguserService.getAll();
    }

    @PostMapping("/add")
    public XephanguserResponse create(@RequestBody XephanguserRequest request) {
        return xephanguserService.create(request);
    }

    @PutMapping("/update/{id}")
    public XephanguserResponse update(@PathVariable Long id, @RequestBody XephanguserRequest request) {
        return xephanguserService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return xephanguserService.delete(id);
    }
}
