package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanhang.dto.response.ThuonghieudienthoaiResponse;
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
    public List<ThuonghieudienthoaiResponse> getAllThuonghieudienthoai() {
        return thuonghieudienthoaiService.getAllThuonghieudienthoai();
    }
}
