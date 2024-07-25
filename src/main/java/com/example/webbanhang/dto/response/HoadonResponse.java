package com.example.webbanhang.dto.response;

import jakarta.persistence.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoadonResponse {
    private Long id;
    private String mahd;
    private String diachi;
    private double tongtien;
    private String userId;
}
