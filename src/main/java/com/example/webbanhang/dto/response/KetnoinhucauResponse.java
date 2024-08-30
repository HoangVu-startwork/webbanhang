package com.example.webbanhang.dto.response;

import jakarta.persistence.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KetnoinhucauResponse {
    private Long id;
    private Long dienthoaiId;
    private Long nhucaudienthoaiId;
}
