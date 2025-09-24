package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.webbanhang.dto.request.GiohangRequest;
import com.example.webbanhang.dto.request.GiohangsRequest;
import com.example.webbanhang.dto.response.GiohangResponse;
import com.example.webbanhang.entity.Giohang;

@Mapper(componentModel = "spring")
public interface GiohangMapper {

    Giohang toGiohang(GiohangRequest request);

    Giohang toGiohangs(GiohangsRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "dienthoai.tensanpham", target = "tensanpham")
    @Mapping(source = "mausac.id", target = "mausacId")
    @Mapping(source = "mausac.tenmausac", target = "tenmausac")
    @Mapping(target = "mausacGiaban", source = "mausac.giaban")
    @Mapping(target = "mausacHinhanh", source = "mausac.hinhanh")
    GiohangResponse toGiohangResponse(Giohang savedGiohang);

    @Named("longToString")
    default String longToString(Long id) {
        return id != null ? id.toString() : null;
    }
}
