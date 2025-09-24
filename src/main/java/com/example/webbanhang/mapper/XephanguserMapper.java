package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;

import com.example.webbanhang.dto.request.XephanguserRequest;
import com.example.webbanhang.dto.response.XephanguserResponse;
import com.example.webbanhang.entity.Xephanguser;

@Mapper(componentModel = "spring")
public interface XephanguserMapper {

    Xephanguser toEntity(XephanguserRequest request);

    XephanguserResponse toResponse(Xephanguser xephanguser);
}
