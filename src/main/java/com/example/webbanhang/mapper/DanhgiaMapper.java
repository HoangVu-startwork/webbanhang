package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.DanhgiaRequest;
import com.example.webbanhang.dto.response.DanhgiaResponse;
import com.example.webbanhang.entity.Danhgia;

@Mapper(componentModel = "spring")
public interface DanhgiaMapper {

    Danhgia toDanhgia(DanhgiaRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "user.username", target = "userName")
    DanhgiaResponse toDanhgiaResponse(Danhgia danhgia);
}
