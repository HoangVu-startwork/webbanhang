package com.example.webbanhang.exception;

public class AppException extends RuntimeException {

    public AppException(ErrorCode errorcode) {
        super(errorcode.getMessage()); // kế thừa từ ErrorCode
        this.errorcode = errorcode;
    }

    private ErrorCode errorcode;

    public ErrorCode getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(ErrorCode errorcode) {
        this.errorcode = errorcode;
    }
}
