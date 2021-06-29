package com.li.oauth.auth.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.oauth.base.security.SmsCodeAuthenticationToken;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/24 15:30
 * @Description: 短信验证码过滤器  类似于{@link “}
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 只接受POST方式
     */
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/users/login/sms", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 请求方式校验
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 获取请求中的参数值
        String mobile = request.getParameter("phone");
        String inCode = request.getParameter("code");
        if (mobile == null || inCode == null) {
            //writerObj(response, BaseResponse.Fail("请输入正确的手机号和密码"));
            throw new AuthenticationServiceException("请输入正确的手机号和密码");
        }
        String code = stringRedisTemplate.opsForValue().get(mobile);
        if (!inCode.equalsIgnoreCase(code)) {
            //writerObj(response, BaseResponse.Fail("验证码不正确"));
            throw new AuthenticationServiceException("验证码不正确");
        }
        mobile = mobile.trim();

        // 验证redis存储的值和传递的值是否一样

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);


        /**
         * 通过 {@link ProvierMagaer} 调用{@link SmsCodeAuthenticationProvider}
         */
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    private void writerObj(HttpServletResponse response, Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            Writer writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(obj));
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
