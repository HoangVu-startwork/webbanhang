package com.example.webbanhang.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.UserCreationRequest;
import com.example.webbanhang.dto.request.UserUpdateRequest;
import com.example.webbanhang.dto.response.RankCountResponse;
import com.example.webbanhang.dto.response.UserResponse;
import com.example.webbanhang.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Email: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUser())
                .build();
    }

    @GetMapping("/{userId}")
    UserResponse getUserid(@PathVariable("userId") String userId) {
        return userService.getUserid(userId);
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    //    UserResponse getUserid(@PathVariable("userId") String userId){
    //        return userService.getUserid(userId);
    //    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User has been deleted";
    }

    @PutMapping("password/{userId}")
    UserResponse updatePasswrd(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updatePassword(userId, request);
    }

    @GetMapping("/allUser")
    ApiResponse<List<UserResponse>> getAllUserAs() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.AllUser())
                .build();
    }

    // Tổng số lượng user xếp hạng
    @GetMapping("/rank-hangmuc")
    public List<RankCountResponse> getUserCountsByRank() {
        return userService.getUserCountByRank();
    }
}
