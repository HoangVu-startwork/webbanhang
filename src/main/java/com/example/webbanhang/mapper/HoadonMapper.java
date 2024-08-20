package com.example.webbanhang.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.HoadonRequest;
import com.example.webbanhang.dto.response.ChitiethoadonResponse;
import com.example.webbanhang.dto.response.HoadonResponse;
import com.example.webbanhang.entity.Chitiethoadon;
import com.example.webbanhang.entity.Hoadon;

@Mapper(componentModel = "spring")
public interface HoadonMapper {

    Hoadon toHoadon(HoadonRequest request);

    HoadonResponse toHoadonResponse(Hoadon savedHoadon);

    @Mapping(target = "dienthoaiTensanpham", source = "dienthoai.tensanpham")
    @Mapping(target = "dienthoaiHinhanh", source = "dienthoai.hinhanh")
    @Mapping(target = "mausacTen", source = "mausac.tenmausac")
    @Mapping(target = "mausacGiaban", source = "mausac.giaban")
    @Mapping(target = "mausacHinhanh", source = "mausac.hinhanh")
    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "mausac.id", target = "mausacId")
    ChitiethoadonResponse toChitiethoadonResponse(Chitiethoadon chitiethoadon);

    List<ChitiethoadonResponse> toChitiethoadonResponseList(List<Chitiethoadon> chitiethoadons);
}
