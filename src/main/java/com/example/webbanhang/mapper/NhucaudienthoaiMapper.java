package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;

import com.example.webbanhang.dto.request.NhucaudienthoaiRequest;
import com.example.webbanhang.dto.response.NhucaudienthoaiResponse;
import com.example.webbanhang.entity.Nhucaudienthoai;

@Mapper(componentModel = "spring")
public interface NhucaudienthoaiMapper {

    NhucaudienthoaiResponse toNhucaudienthoaiResponse(Nhucaudienthoai request);

    Nhucaudienthoai toNhucaudienthoai(NhucaudienthoaiRequest request);
}
