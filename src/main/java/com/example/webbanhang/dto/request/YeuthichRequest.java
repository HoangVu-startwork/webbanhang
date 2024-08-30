package com.example.webbanhang.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YeuthichRequest {
    private Long id;
    private Long dienthoaiId;
    private Long mausacId;
    private String userId;
}
