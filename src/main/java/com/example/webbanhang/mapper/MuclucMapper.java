package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;

import com.example.webbanhang.dto.request.MuclucRequest;
import com.example.webbanhang.dto.response.MuclucResponse;
import com.example.webbanhang.entity.Mucluc;

@Mapper(componentModel = "spring")
public interface MuclucMapper {
    Mucluc toMucluc(MuclucRequest request);

    MuclucResponse toMuclucResponse(Mucluc savedMucluc);
}
