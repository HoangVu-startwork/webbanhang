package com.example.webbanhang.exception;

public enum ErrorCode {
    SIZE_PASSWORD(1002, "Mật khẩu phải có ít nhất 8 ký tự."),
    SO_PASSWORD(1003, "Mật khẩu phải chứa ít nhất một chữ số."),
    CHUTHUONG_PASSWORD(1004, "Mật khẩu phải chứa ít nhất một chữ thưởng."),
    CHUHOA_PASSWORD(1005, "Mật khẩu phải chứa ít nhất một chữ hoa."),
    KYTUDATBIET_PASSWORD(1006, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt."),
    KHOANGTRANG_PASSWORD(1007, "Mật khẩu không được chứa khoảng trắng."),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    USER_EXISTED(1001, "User existed --- "),
    INVALID_KEY(1999, "Lỗi Key không đúng");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
