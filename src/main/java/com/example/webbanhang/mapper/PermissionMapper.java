package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;

import com.example.webbanhang.dto.request.PermissionRequest;
import com.example.webbanhang.dto.response.PermissionResponse;
import com.example.webbanhang.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
