package com.example.webbanhang.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhgiaRequest {
    private Long id;
    private String chitiet;

    @Min(value = 0, message = "Điểm đánh giá phải từ 0 trở lên")
    @Max(value = 5, message = "Điểm đánh giá phải từ 5 trở xuống")
    private double diem;

    private String dob;
    private Long dienthoaiId;
    private String userId;
}
