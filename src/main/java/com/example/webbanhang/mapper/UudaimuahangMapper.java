package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.UudaimuahangRequest;
import com.example.webbanhang.dto.response.UudaimuahangResponse;
import com.example.webbanhang.entity.Uudaimuahang;

@Mapper(componentModel = "spring")
public interface UudaimuahangMapper {

    @Mapping(target = "xephanguserId", source = "xephanguser.id")
    UudaimuahangResponse toResponse(Uudaimuahang uudaimuahang);

    @Mapping(target = "xephanguser", ignore = true)
    Uudaimuahang toEntity(UudaimuahangRequest request);
}
