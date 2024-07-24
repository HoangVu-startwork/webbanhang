package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.webbanhang.dto.request.GiohangRequest;
import com.example.webbanhang.dto.response.GiohangResponse;
import com.example.webbanhang.entity.Giohang;

@Mapper(componentModel = "spring")
public interface GiohangMapper {

    Giohang toGiohang(GiohangRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "mausac.id", target = "mausacId")
    @Mapping(source = "user.id", target = "userId", qualifiedByName = "longToString")
    GiohangResponse toGiohangResponse(Giohang savedGiohang);

    @Named("longToString")
    default String longToString(Long id) {
        return id != null ? id.toString() : null;
    }
}
