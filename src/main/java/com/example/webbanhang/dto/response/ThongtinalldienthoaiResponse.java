package com.example.webbanhang.dto.response;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongtinalldienthoaiResponse {
    private Long id;
    private String tensanpham;
    private String hinhanh;
    private List<String> hinhanhduyet;
    private String ram;
    private String bonho;
    private String giaban;
    private List<MausachienthoaiResponse> mausacs;
    private List<MausachienthoaiResponse> mausactong;
    private ThongsokythuatdienthoaiResponse thongsokythuats;
    private ThongtindienthoaidienthoaiResponse thongtindienthoai;
    private String soluongTonKho;
}
