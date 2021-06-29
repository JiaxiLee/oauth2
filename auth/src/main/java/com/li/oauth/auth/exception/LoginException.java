package com.li.oauth.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * description: LoginException
 * date: 2021/6/2
 * author: lijiaxi-os
 */
public class LoginException extends AuthenticationException {

    public LoginException(String msg, Throwable t) {
        super(msg, t);
    }

    public LoginException(String msg) {
        super(msg);
    }
}
