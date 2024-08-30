package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.KetnoinhucauRequest;
import com.example.webbanhang.dto.response.KetnoinhucauResponse;
import com.example.webbanhang.entity.Ketnoinhucau;

@Mapper(componentModel = "spring")
public interface KetnoinhucauMapper {
    @Mapping(source = "dienthoaiId", target = "dienthoai.id")
    @Mapping(source = "nhucaudienthoaiId", target = "nhucaudienthoai.id")
    Ketnoinhucau toKetnoinhucau(KetnoinhucauRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "nhucaudienthoai.id", target = "nhucaudienthoaiId")
    KetnoinhucauResponse toKetnoinhucauResponse(Ketnoinhucau entity);
}
