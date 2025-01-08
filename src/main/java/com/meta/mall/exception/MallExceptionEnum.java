package com.meta.mall.exception;

public enum MallExceptionEnum {
    NEED_USER_NAME(10001, "username is required"),
    NEED_PASSWORD(10002, "password is required"),
    PASSWORD_TOO_SHORT(10003, "at least 8 digit"),
    DUPLICATE_USERNAME(10004, "duplicate username"),
    WRONG_PASSWORD(10005, "wrong password"),
    NEED_LOGIN(10006, "need login"),
    UPDATE_SIGNATURE_FAIL(10007, "user signature update fail"),

    NEED_ADMIN(10008, "not admin"),

    PARAM_NULL(10009, "param shouldn't be null"),
    CATEGORY_NAME_EXIST(10010, "category name exist"),

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
