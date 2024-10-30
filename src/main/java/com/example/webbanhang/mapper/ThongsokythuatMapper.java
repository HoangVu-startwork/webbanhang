package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.ThongsokythuatRequest;
import com.example.webbanhang.dto.request.ThongsokythuatsRequest;
import com.example.webbanhang.dto.response.ThongsokythuatResponse;
import com.example.webbanhang.entity.Thongsokythuat;

@Mapper(componentModel = "spring")
public interface ThongsokythuatMapper {
    Thongsokythuat toThongsokythuat(ThongsokythuatRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    ThongsokythuatResponse toThongsokythuatResponse(Thongsokythuat savedThongsokythuat);

    Thongsokythuat upThongsokythuat(ThongsokythuatsRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    ThongsokythuatResponse upThongsokythuatResponse(Thongsokythuat savedThongsokythuat);
}
