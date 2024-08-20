package com.example.webbanhang.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.BinhluandienthoaiRequest;
import com.example.webbanhang.dto.response.*;
import com.example.webbanhang.entity.Binhluandienthoai;
import com.example.webbanhang.service.BinhluandienthoaiService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/binhluan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BinhluandienthoaiController {
    private final BinhluandienthoaiService binhluandienthoaiService;

    // Lấy tất cả các bình luận gốc của một sản phẩm
    // Lấy tất cả các phản hồi của một bình luận

    @GetMapping("/product/{dienthoaiId}")
    public ApiResponse<List<BinhluandienthoaiResponse>> getAllCommentsByDienthoaiId(@PathVariable Long dienthoaiId) {
        return ApiResponse.<List<BinhluandienthoaiResponse>>builder()
                .result(binhluandienthoaiService.getAllCommentsByDienthoaiId(dienthoaiId))
                .build();
    }

    @GetMapping("/{commentId}/replies")
    public ApiResponse<List<BinhluandienthoaiResponse>> getRepliesForComment(@PathVariable Long commentId) {
        return ApiResponse.<List<BinhluandienthoaiResponse>>builder()
                .result(binhluandienthoaiService.getRepliesForComment(commentId))
                .build();
    }

    @PostMapping
    public ApiResponse<Binhluandienthoai> addComment(@RequestBody BinhluandienthoaiRequest request) {
        return ApiResponse.<Binhluandienthoai>builder()
                .result(binhluandienthoaiService.addComment(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BinhluandienthoaiResponse> getAll1(@PathVariable Long id) {
        Optional<BinhluandienthoaiResponse> comment = binhluandienthoaiService.getCommentById(id);
        return ApiResponse.<BinhluandienthoaiResponse>builder()
                .result(comment.orElse(null))
                .build();
    }

    @GetMapping("/product/{dienthoaiId}/all")
    public ApiResponse<List<BinhluandienthoaiResponse>> getAllCommentsAndRepliesByDienthoaiId(
            @PathVariable Long dienthoaiId) {
        return ApiResponse.<List<BinhluandienthoaiResponse>>builder()
                .result(binhluandienthoaiService.getAllCommentsAndRepliesByDienthoaiId(dienthoaiId))
                .build();
    }
}
