package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.MausacRequest;
import com.example.webbanhang.dto.response.MausacResponse;
import com.example.webbanhang.entity.Mausac;

@Mapper(componentModel = "spring")
public interface MausacMapper {
    Mausac toMausac(MausacRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    MausacResponse toMausacResponse(Mausac savedMausac);
}
