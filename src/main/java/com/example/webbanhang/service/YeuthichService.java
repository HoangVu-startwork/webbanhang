package com.example.webbanhang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.YeuthichRequest;
import com.example.webbanhang.dto.response.YeuthichResponse;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.entity.Yeuthich;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.YeuthichMapper;
import com.example.webbanhang.repository.UserRepository;
import com.example.webbanhang.repository.YeuthichRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class YeuthichService {
    YeuthichRepository yeuthichRepository;
    YeuthichMapper yeuthichMapper;
    UserRepository userRepository;

    public YeuthichResponse addYeuthich(YeuthichRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Yeuthich exyeuthich = yeuthichRepository.findByUserIdAndDienthoaiIdAndMausacId(
                request.getUserId(), request.getDienthoaiId(), request.getMausacId());
        if (exyeuthich != null) {
            throw new AppException(ErrorCode.YEUTHICH_EXISTED);
        }
        Yeuthich yeuthich = yeuthichMapper.toYeuthich(request);
        yeuthich.setUser(user);
        Yeuthich savedYeuthich = yeuthichRepository.save(yeuthich);
        return yeuthichMapper.toYeuthichResponse(savedYeuthich);
    }

    public List<YeuthichResponse> getYeuthichByUserId(String userId) {
        Optional<User> userlist = userRepository.findById(userId);
        if (userlist != null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        List<Yeuthich> yeuthichlist = yeuthichRepository.findAllByUserId(userId);
        return yeuthichlist.stream().map(yeuthichMapper::toYeuthichResponse).toList();
    }
}
