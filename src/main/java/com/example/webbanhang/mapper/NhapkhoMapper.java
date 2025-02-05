package com.example.webbanhang.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.webbanhang.dto.request.NhapkhoRequest;
import com.example.webbanhang.dto.request.NhapkhosRequest;
import com.example.webbanhang.dto.response.NhapkhosResponse;
import com.example.webbanhang.entity.Nhapkho;

@Mapper(
        componentModel = "spring",
        uses = {DienthoaiMapper.class, MausacMapper.class, UserMapper.class})
public interface NhapkhoMapper {

    @Mapping(source = "dob", target = "dob", dateFormat = "yyyy-MM-dd")
    Nhapkho toGiohang(NhapkhoRequest request);

    @Mapping(source = "dob", target = "dob", dateFormat = "yyyy-MM-dd")
    Nhapkho toGiohangs(NhapkhosRequest request);

    @Mapping(source = "dienthoai.id", target = "dienthoaiId")
    @Mapping(source = "mausac.id", target = "mausacId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "dienthoai.tensanpham", target = "tenSanPham")
    @Mapping(source = "mausac.tenmausac", target = "tenMauSac")
    NhapkhosResponse toNhapkhoResponse(Nhapkho savedNhapkho);
}
