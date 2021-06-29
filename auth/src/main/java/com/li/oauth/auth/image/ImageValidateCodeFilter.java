package com.li.oauth.auth.image;

import com.li.oauth.auth.constant.SecurityConstants;
import com.li.oauth.auth.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: ValidateCodeFilter
 * date: 2020/7/30
 * author: lijiaxi-os
 */
@Component("imageValidateCodeFilter")
@Slf4j
public class ImageValidateCodeFilter extends OncePerRequestFilter {

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 返回true代表不执行过滤器，false代表执行
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        System.out.println(request.getRequestURI() + "=====" + request.getRequestURL());
        System.out.println(pathMatcher.match(SecurityConstants.IMAGE_LOGIN_URL, request.getRequestURI()));
        if (pathMatcher.match(SecurityConstants.IMAGE_LOGIN_URL, request.getRequestURI())) {
            //判断是否有不验证验证码的client
            if (securityProperties.getCode().getIgnoreClientCode().length > 0) {
                try {
                    String clientId = request.getHeader("client_id");
                    for (String client : securityProperties.getCode().getIgnoreClientCode()) {
                        if (client.equals(clientId)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    log.error("解析client信息失败", e);
                }
            }
            return false;
        }

        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {
        log.info("do filter validate code ...");
        try {
            // 获取请求中的参数值
            String inCode = request.getParameter("code");
            if (StringUtils.isBlank(inCode)) {
                //writerObj(response, BaseResponse.Fail("请输入正确的手机号和密码"));
                throw new AuthenticationServiceException("请输入正确的验证码");
            }
            String code = (String) request.getSession().getAttribute(SecurityConstants.KAPTCHA_SESSION_KEY);//stringRedisTemplate.opsForValue().get(mobile);
            if (!inCode.equalsIgnoreCase(code)) {
                //writerObj(response, BaseResponse.Fail("验证码不正确"));
                throw new AuthenticationServiceException("验证码不正确");
            }

        } catch (AuthenticationException e) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
