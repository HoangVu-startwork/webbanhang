package com.example.webbanhang.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public enum ErrorCode {
    SIZE_PASSWORD(1002, "Mật khẩu phải có ít nhất 8 ký tự.", HttpStatus.BAD_GATEWAY),
    SO_PASSWORD(1003, "Mật khẩu phải chứa ít nhất một chữ số.", HttpStatus.BAD_GATEWAY),
    CHUTHUONG_PASSWORD(1004, "Mật khẩu phải chứa ít nhất một chữ thưởng.", HttpStatus.BAD_GATEWAY),
    CHUHOA_PASSWORD(1005, "Mật khẩu phải chứa ít nhất một chữ hoa.", HttpStatus.BAD_GATEWAY),
    KYTUDATBIET_PASSWORD(1006, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt.", HttpStatus.BAD_GATEWAY),
    KHOANGTRANG_PASSWORD(1007, "Mật khẩu không được chứa khoảng trắng.", HttpStatus.BAD_GATEWAY),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User existed --- ", HttpStatus.BAD_GATEWAY),
    USER_NOT_EXISTED(1008, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Đăng nhập thành công", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(10011, "You do not have permission", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(1010, "Gửi email bị lỗi", HttpStatus.BAD_GATEWAY),
    INVALID_KEY(1999, "Lỗi Key không đúng", HttpStatus.BAD_GATEWAY),
    INVALID_DOB(2010, "Ngày sinh không hợp lệ {min}", HttpStatus.BAD_REQUEST);


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
