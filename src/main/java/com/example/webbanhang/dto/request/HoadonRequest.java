package com.example.webbanhang.dto.request;

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
public class HoadonRequest {
    private String email;
    private String diachi;
}
