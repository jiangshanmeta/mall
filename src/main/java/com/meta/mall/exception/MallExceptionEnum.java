package com.meta.mall.exception;

public enum MallExceptionEnum {
    NEED_USER_NAME(10001, "username is required"),
    NEED_PASSWORD(10002, "password is required"),
    PASSWORD_TOO_SHORT(10003, "at least 8 digit"),
    DUPLICATE_USERNAME(10004, "duplicate username"),


    SYSTEM_ERROR(20000, "system error");


    final Integer code;
    final String msg;

    MallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
