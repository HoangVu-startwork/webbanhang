package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.LoaisanphamRequest;
import com.example.webbanhang.dto.response.LoaisanphamResponse;
import com.example.webbanhang.entity.Loaisanpham;

@Mapper(componentModel = "spring")
public interface LoaisanphamMapper {
    Loaisanpham toLoaisanpham(LoaisanphamRequest request);

    @Mapping(source = "danhmuc.id", target = "danhmucId")
    LoaisanphamResponse toLoaisanphamResponse(Loaisanpham loaisanpham);
}
