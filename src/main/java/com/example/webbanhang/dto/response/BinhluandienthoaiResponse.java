package com.example.webbanhang.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BinhluandienthoaiResponse {
    private Long id;
    private String chitiet;
    private Long dienthoaiId;
    private String userId;
    private Long parentCommentId;
    private List<BinhluandienthoaiResponse> replies;
}
