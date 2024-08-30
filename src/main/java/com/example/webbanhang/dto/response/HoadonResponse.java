package com.example.webbanhang.dto.response;

import java.util.List;

import jakarta.persistence.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HoadonResponse {
    private Long id;
    private String mahd;
    private String diachi;
    private double tongtien;
    private String dob;
    private String userId;
    private List<ChitiethoadonResponse> chitiethoadons;
}
