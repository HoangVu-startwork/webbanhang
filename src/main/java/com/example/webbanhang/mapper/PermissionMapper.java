package com.example.webbanhang.mapper;


import com.example.webbanhang.dto.request.PermissionRequest;
import com.example.webbanhang.dto.response.PermissionResponse;
import com.example.webbanhang.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
