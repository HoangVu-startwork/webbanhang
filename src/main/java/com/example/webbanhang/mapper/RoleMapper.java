package com.example.webbanhang.mapper;


import com.example.webbanhang.dto.request.RoleRequest;
import com.example.webbanhang.dto.response.RoleResponse;
import com.example.webbanhang.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}