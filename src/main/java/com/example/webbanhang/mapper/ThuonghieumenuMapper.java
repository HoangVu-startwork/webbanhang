package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.response.ThuonghieumenuResponse;
import com.example.webbanhang.dto.response.ThuonghieumenusResponse;
import com.example.webbanhang.entity.Thuonghieumenu;

@Mapper(componentModel = "spring")
public interface ThuonghieumenuMapper {
    Thuonghieumenu toThuonghieumenu(Thuonghieumenu request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    ThuonghieumenuResponse toThuonghieumenuResponse(Thuonghieumenu thuonghieumenu);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "dienthoai.tensanpham", target = "tenSanPham")
    ThuonghieumenusResponse toThuonghieumenusResponse(Thuonghieumenu thuonghieumenu);
}
