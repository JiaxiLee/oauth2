package com.li.oauth.auth.properties;


import com.li.oauth.auth.security.LoginResponseType;
import lombok.Getter;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/18 15:21
 * @Description:    浏览器登录认证相关配置
 */
@Getter
public class BrowserProperties {


    /**
     * 默认登录页面
     */


    /**
     * 默认登录方式
     */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /**
     * 默认记住我的时长
     */
    private int rememberMeSeconds = 3600;


}
