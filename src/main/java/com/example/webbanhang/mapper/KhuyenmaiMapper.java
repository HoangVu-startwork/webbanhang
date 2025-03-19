package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.KhuyenmaiRequest;
import com.example.webbanhang.dto.request.KhuyenmaisRequest;
import com.example.webbanhang.dto.response.KhuyenmaiResponse;
import com.example.webbanhang.entity.Khuyenmai;

@Mapper(componentModel = "spring")
public interface KhuyenmaiMapper {
    Khuyenmai toKhuyenmai(KhuyenmaiRequest request);

    Khuyenmai toKhuyenmaiId(KhuyenmaisRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "id", target = "id")
    KhuyenmaiResponse toKhuyenmaiResponse(Khuyenmai savedKhuyenmai);
}
