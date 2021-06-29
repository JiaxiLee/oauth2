package com.li.oauth.auth.image;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * description: ImageValidateSecurityConfig
 * date: 2021/6/5
 * author: lijiaxi-os
 */
@Component
public class ImageValidateSecurityConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Resource
    private Filter imageValidateCodeFilter;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(imageValidateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }
}
