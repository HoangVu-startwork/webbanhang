package com.example.webbanhang.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class XephanguserRequest {
    private String hangmuc;
    private double giatien;
}
