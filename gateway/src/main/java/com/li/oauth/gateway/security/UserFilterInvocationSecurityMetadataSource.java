package com.li.oauth.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.oauth.gateway.entity.Menu;
import com.li.oauth.gateway.properties.SecurityProperties;
import com.li.oauth.gateway.service.MenuService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * description: UserFilterInvocationSecurityMetadataSource
 * date: 2021/5/31
 * author: lijiaxi-os
 */
@Component
@Slf4j
public class UserFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    @Autowired
    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @SneakyThrows
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        ObjectMapper objectMapper = new ObjectMapper();

        //获取请求起源路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //String urls = ((FilterInvocation) object).getRequestUrl();
        // System.out.println(urls +"----------");
        log.info("VFilterInvocationSecurityMetadataSource getAttributes [requestUrl={}]", requestUrl);
        //登录页面就不需要权限
        for (String url : securityProperties.getIgnore().getUrls()) {
            if (pathMatcher.match(url, requestUrl)) {
                return null;
            }
        }

        //用来存储访问路径需要的角色或权限信息
        List<String> tempPermissionList = new ArrayList<>();
        //获取角色列表
        Menu menu = menuService.findByUrl(requestUrl);
        if (menu == null) {
            return null;
        }
        tempPermissionList.add(menu.getMenuCode().toString());
        //如果没有权限控制的url，可以设置都可以访问，也可以设置默认不许访问
       /* if (tempPermissionList.isEmpty()) {
            return null;//都可以访问
            //tempPermissionList.add("DEFAULT_FORBIDDEN");//默认禁止
        }*/
        String[] permissionArray = tempPermissionList.toArray(new String[0]);
        log.info("VFilterInvocationSecurityMetadataSource getAttributes [permissionArray={}]", objectMapper.writeValueAsString(permissionArray));
        return SecurityConfig.createList(permissionArray);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }


    public static void main(String[] args) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        System.out.println(pathMatcher.match("**/login/ddd", "user/login/ddd"));
    }
}
