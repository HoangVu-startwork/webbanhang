package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.response.ThuonghieumenuResponse;
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
}
