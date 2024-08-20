package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.response.ThuonghieudienthoaiResponse;
import com.example.webbanhang.entity.Thuonghieudienthoai;

@Mapper(componentModel = "spring")
public interface ThuonghieudienthoaiMapper {
    Thuonghieudienthoai toThuonghieudienthoai(Thuonghieudienthoai request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    ThuonghieudienthoaiResponse toThuonghieudienthoaiResponse(Thuonghieudienthoai thuonghieudienthoai);
}
