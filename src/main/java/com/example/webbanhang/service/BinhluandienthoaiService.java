package com.example.webbanhang.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.BinhluandienthoaiRequest;
import com.example.webbanhang.dto.response.BinhluandienthoaiResponse;
import com.example.webbanhang.entity.Binhluandienthoai;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.mapper.BinhluandienthoaiMapper;
import com.example.webbanhang.repository.BinhluandienthoaiRepository;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BinhluandienthoaiService {

    BinhluandienthoaiRepository binhluandienthoaiRepository;
    UserRepository userRepository;
    DienthoaiRepository dienthoaiRepository;
    BinhluandienthoaiMapper binhluandienthoaiMapper;

    public List<BinhluandienthoaiResponse> getAllCommentsByDienthoaiId(Long dienthoaiId) {
        return binhluandienthoaiRepository.findByDienthoaiIdAndParentCommentIsNull(dienthoaiId).stream()
                .map(binhluandienthoaiMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<BinhluandienthoaiResponse> getRepliesForComment(Long parentCommentId) {
        return binhluandienthoaiRepository.findByParentCommentId(parentCommentId).stream()
                .map(binhluandienthoaiMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Binhluandienthoai addComment(BinhluandienthoaiRequest request) {
        Binhluandienthoai comment = binhluandienthoaiMapper.toEntity(request);

        // Xử lý thêm nếu cần
        if (request.getDienthoaiId() != null) {
            Dienthoai dienthoai = dienthoaiRepository
                    .findById(request.getDienthoaiId())
                    .orElseThrow(() -> new RuntimeException("Dienthoai not found"));
            comment.setDienthoai(dienthoai);
        }

        if (request.getUserId() != null) {
            User user = userRepository
                    .findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            comment.setUser(user);
        }

        if (request.getParentCommentId() != null) {
            Binhluandienthoai parentComment = binhluandienthoaiRepository
                    .findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        return binhluandienthoaiRepository.save(comment);
    }

    public Optional<BinhluandienthoaiResponse> getCommentById(Long id) {
        return binhluandienthoaiRepository.findById(id).map(binhluandienthoaiMapper::toResponse);
    }

    public List<BinhluandienthoaiResponse> getAllCommentsAndRepliesByDienthoaiId(Long dienthoaiId) {
        List<BinhluandienthoaiResponse> rootComments = getAllCommentsByDienthoaiId(dienthoaiId);
        rootComments.forEach(this::populateReplies);
        return rootComments;
    }

    private void populateReplies(BinhluandienthoaiResponse commentResponse) {
        List<BinhluandienthoaiResponse> replies = getRepliesForComment(commentResponse.getId());
        commentResponse.setReplies(replies);
        replies.forEach(this::populateReplies);
    }
}
