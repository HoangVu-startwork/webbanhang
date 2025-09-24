package com.example.webbanhang.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateQuantityRequest {
    @NotNull
    private Long giohangId;

    @Min(1)
    private Integer quantity;
}
