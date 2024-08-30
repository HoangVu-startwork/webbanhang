package com.example.webbanhang.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhgiaResponse {
    private Long id;
    private String chitiet;
    private double diem;
    private String dob;
    private Long dienthoaiId;
    private String userId;
    private String userEmail;
    private String userName;
}
