package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.response.DienthoaiResponse;
import com.example.webbanhang.entity.Dienthoai;

@Mapper(componentModel = "spring")
public interface DienthoaiMapper {
    Dienthoai toDienthoai(DienthoaiRequest request);

    @Mapping(source = "thongtinphanloai.id", target = "thongtinphanloaiId")
    DienthoaiResponse toDienthoaiResponse(Dienthoai savedDienthoai);
}
