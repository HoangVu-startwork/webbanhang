package com.example.webbanhang.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhapkhoResponse {
    private Long dienthoaiId;
    private String soluong;
    private Long mausacId;
    private String userId;
    private String dob;
}
