package com.example.webbanhang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BinhluandienthoaiRequest {
    private Long id;
    private String chitiet;
    private Long dienthoaiId;
    private String userId;
    private Long parentCommentId;
}
