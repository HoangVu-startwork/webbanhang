package com.example.webbanhang.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongtinalldienthoaiResponse {
    private Long id;
    private String tensanpham;
    private String hinhanh;
    private List<String> hinhanhduyet;
    private String ram;
    private String bonho;
    private String giaban;
    private List<MausachienthoaiResponse> mausacs;
    private ThongsokythuatdienthoaiResponse thongsokythuats;
    private ThongtindienthoaidienthoaiResponse thongtindienthoai;
}
