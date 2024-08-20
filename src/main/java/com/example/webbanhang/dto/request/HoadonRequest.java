package com.example.webbanhang.dto.request;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

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

    @NotEmpty
    List<Long> productIds;
}
