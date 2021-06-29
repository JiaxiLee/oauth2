package com.li.oauth.auth.constant;

/**
 * 返回码描述
 */
public enum RtnEnum {

    SUCCESS(0, "成功"),
    CODE_FAIL(1, "数据操作失败"),
    CODE_EXCEPTION(2, "操作异常"),
    USER_NAME_EXIST(1001, "用户名已存在"),
    USER_PHONE_EXIST(1002, "手机号已存在"),
   ;

    private Integer code;

    private String msg;

    RtnEnum(Integer code, String msg) {
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