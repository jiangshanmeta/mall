package com.meta.mall.exception;

public class MallException extends RuntimeException {
    private final Integer code;
    private final String msg;

    public MallException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MallException(MallExceptionEnum ex) {
        this.code = ex.getCode();
        this.msg = ex.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
