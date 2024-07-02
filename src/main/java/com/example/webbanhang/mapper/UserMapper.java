package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.webbanhang.dto.request.UserCreationRequest;
import com.example.webbanhang.dto.request.UserUpdateRequest;
import com.example.webbanhang.dto.response.UserResponse;
import com.example.webbanhang.entity.User;

// @Mapper(componentModel = "spring")
// public interface UserMapper {
//    User toUser(UserCreationRequest request);
//
//    //@Mapping(source = "firstName", target = "lastName") // gán giá trị của lastName vào firstName
//    //@Mapping(source = "firstName", ignore = true) // cái này làm không cho giá tri  firstName không hien lên khi trã
// dữ liệu về
//    UserResponse toUserResponse(User user);
//
//    @Mapping(target = "roles", ignore = true)
//    void updateUser(@MappingTarget User user, UserUpdateRequest request);
// }

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
