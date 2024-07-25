package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.KhuyenmaiRequest;
import com.example.webbanhang.dto.response.KhuyenmaiResponse;
import com.example.webbanhang.entity.Khuyenmai;

@Mapper(componentModel = "spring")
public interface KhuyenmaiMapper {
    Khuyenmai toKhuyenmai(KhuyenmaiRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    KhuyenmaiResponse toKhuyenmaiResponse(Khuyenmai savedKhuyenmai);
}
