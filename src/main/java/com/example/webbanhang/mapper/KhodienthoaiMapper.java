package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.KhodienthoaiRequest;
import com.example.webbanhang.dto.response.KhodienthoaiResponse;
import com.example.webbanhang.entity.Khodienthoai;

@Mapper(componentModel = "spring")
public interface KhodienthoaiMapper {
    Khodienthoai toKhodienthoai(KhodienthoaiRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "mausac.id", target = "mausacId")
    KhodienthoaiResponse toKhodienthoaiResponse(Khodienthoai savedKhodienthoai);
}
