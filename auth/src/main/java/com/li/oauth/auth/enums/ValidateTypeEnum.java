package com.li.oauth.auth.enums;

import lombok.Getter;

/**
 * description: ValidateTypeEnum
 * date: 2020/7/30
 * author: lijiaxi-os
 */
@Getter
public enum ValidateTypeEnum {
    /**
     * 图形验证码
     */
    IMAGE("image", "/uaa/users/login/image"),
    /**
     * 短信验证码
     */
    SMS("sms", "/uaa/users/login/sms")
    ;
    private String type;
    private String name;
    ValidateTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
