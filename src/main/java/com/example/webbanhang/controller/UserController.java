package com.example.webbanhang.controller;


import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.UserCreationRequest;
import com.example.webbanhang.dto.request.UserUpdateRequest;
import com.example.webbanhang.dto.response.UserResponse;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;


    @PostMapping
    ApiResponse<User> create(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    List<User> getUser(){
        return userService.getUser();
    }

    @GetMapping("/{userId}")
    UserResponse getUserid(@PathVariable("userId") String userId){
        return userService.getUserid(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
