package com.li.oauth.gateway.exception;

import com.li.oauth.gateway.common.RtnEnum;
import lombok.Getter;

/**
 * description: InsertUserException
 * date: 2021/5/31
 * author: lijiaxi-os
 */
@Getter
public class InsertUserException extends RuntimeException {

    private Integer code;

    private String msg;

    public InsertUserException(Integer code, String msg) {
        super(msg);
    }

    public InsertUserException(RtnEnum rtnEnum) {
        super(rtnEnum.getMsg());
        this.code = rtnEnum.getCode();
        this.msg = rtnEnum.getMsg();
    }

    public InsertUserException(Throwable e) {
        super(e);
    }

}
