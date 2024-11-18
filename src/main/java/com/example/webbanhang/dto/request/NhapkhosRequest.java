package com.example.webbanhang.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhapkhosRequest {
    private String email;
    private String soluong;
    private Long dienthoaiId;
    private Long mausacId;
    private LocalDate dob;
}
