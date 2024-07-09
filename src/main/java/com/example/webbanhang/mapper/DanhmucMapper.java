package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.DanhmucRequest;
import com.example.webbanhang.dto.response.DanhmucResponse;
import com.example.webbanhang.entity.Danhmuc;

@Mapper(componentModel = "spring")
public interface DanhmucMapper {
    Danhmuc toDanhmuc(DanhmucRequest request);

    @Mapping(source = "mucluc.id", target = "muclucId")
    @Mapping(source = "hedieuhanh.id", target = "hedieuhanhId")
    DanhmucResponse toDanhmucResponse(Danhmuc danhmuc);
}
