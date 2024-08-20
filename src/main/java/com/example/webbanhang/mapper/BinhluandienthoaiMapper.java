package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.BinhluandienthoaiRequest;
import com.example.webbanhang.dto.response.BinhluandienthoaiResponse;
import com.example.webbanhang.entity.Binhluandienthoai;

@Mapper(componentModel = "spring")
public interface BinhluandienthoaiMapper {
    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "parentComment.id", target = "parentCommentId")
    BinhluandienthoaiResponse toResponse(Binhluandienthoai comment);

    @Mapping(source = "dienthoaiId", target = "dienthoai.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "parentCommentId", target = "parentComment.id")
    Binhluandienthoai toEntity(BinhluandienthoaiRequest request);
}
