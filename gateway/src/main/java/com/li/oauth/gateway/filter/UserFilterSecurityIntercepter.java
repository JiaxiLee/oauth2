package com.li.oauth.gateway.filter;

import com.li.oauth.gateway.security.UserAccessDecisionManager;
import com.li.oauth.gateway.security.UserFilterInvocationSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * description: UserFilterSecurityIntercepter
 * date: 2021/5/31
 * author: lijiaxi-os
 */
@Component
@ServletComponentScan
@WebFilter(filterName = "userFilterSecurityIntercepter", urlPatterns = "/*")
@Order(100)
@Slf4j
public class UserFilterSecurityIntercepter extends AbstractSecurityInterceptor implements Filter {


    @Autowired
    private UserFilterInvocationSecurityMetadataSource userFilterInvocationSecurityMetadataSource;

    @Autowired
    private UserAccessDecisionManager userAccessDecisionManager;


    @Autowired
    public void setMyAccessDecisionManager() {
        super.setAccessDecisionManager(userAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("interceptor..............+ req url : " + ((HttpServletRequest) request).getRequestURL());
        FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        invoke(filterInvocation);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        // filterInvocation里面有一个被拦截的url
        // 里面调用VFilterInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取filterInvocation对应的所有权限
        // 再调用VAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(filterInvocation);
        try {
            // 执行下一个拦截器
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(interceptorStatusToken, null);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.userFilterInvocationSecurityMetadataSource;
    }

    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        accessDecisionManager = this.userAccessDecisionManager;
        super.setAccessDecisionManager(accessDecisionManager);
    }
}
