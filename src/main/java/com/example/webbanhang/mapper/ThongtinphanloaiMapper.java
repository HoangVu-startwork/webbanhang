package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.response.ThongtinphanloaiResponse;
import com.example.webbanhang.entity.Thongtinphanloai;

@Mapper(componentModel = "spring")
public interface ThongtinphanloaiMapper {
    Thongtinphanloai toThongtinphanloai(Thongtinphanloai request);

    @Mapping(source = "loaisanpham.id", target = "loaisanphamId")
    ThongtinphanloaiResponse toThongtinphanloaiResponse(Thongtinphanloai thongtinphanloai);
}
