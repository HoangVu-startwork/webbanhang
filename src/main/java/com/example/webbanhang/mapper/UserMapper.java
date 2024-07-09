package com.example.webbanhang.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.webbanhang.dto.request.UserCreationRequest;
import com.example.webbanhang.dto.request.UserUpdateRequest;
import com.example.webbanhang.dto.response.UserResponse;
import com.example.webbanhang.entity.Role;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.repository.RoleRepository;

@Mapper(
        componentModel = "spring",
        imports = {HashSet.class})
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    default Set<Role> mapRoles(List<String> roles) {
        if (roles == null) {
            return Collections.emptySet();
        }
        RoleRepository roleRepository = null;
        return roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)) // Sử dụng repository để tìm role
                .collect(Collectors.toSet());
    }
}
