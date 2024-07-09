package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;

import com.example.webbanhang.dto.request.HedieuhanhRequest;
import com.example.webbanhang.dto.response.HedieuhanhResponse;
import com.example.webbanhang.entity.Hedieuhanh;

@Mapper(componentModel = "spring")
public interface HedieuhanhMapper {
    Hedieuhanh toHedieuhanh(HedieuhanhRequest request);

    HedieuhanhResponse toHedieuhanhResponse(Hedieuhanh savedHedieuhanh);
}
