package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.YeuthichRequest;
import com.example.webbanhang.dto.response.YeuthichResponse;
import com.example.webbanhang.entity.Yeuthich;

@Mapper(componentModel = "spring")
public interface YeuthichMapper {

    @Mapping(source = "dienthoaiId", target = "dienthoai.id")
    @Mapping(source = "mausacId", target = "mausac.id")
    @Mapping(source = "userId", target = "user.id")
    Yeuthich toYeuthich(YeuthichRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "mausac.id", target = "mausacId")
    @Mapping(source = "user.id", target = "userId")
    YeuthichResponse toYeuthichResponse(Yeuthich entity);
}
