package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.ThongtindienthoaiRequest;
import com.example.webbanhang.dto.response.ThongtindienthoaiResponse;
import com.example.webbanhang.entity.Thongtindienthoai;

@Mapper(componentModel = "spring")
public interface ThongtindienthoaiMapper {
    Thongtindienthoai toThongtindienthoai(ThongtindienthoaiRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    ThongtindienthoaiResponse toThongtindienthoaiResponse(Thongtindienthoai savedThongtindienthoai);
}
