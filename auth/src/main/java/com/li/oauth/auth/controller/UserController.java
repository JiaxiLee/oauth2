package com.li.oauth.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.oauth.auth.service.RedisClientDetailsService;
import com.li.oauth.auth.service.UserService;
import com.li.oauth.base.constant.BaseResponse;
import com.li.oauth.base.entity.User;
import com.li.oauth.base.security.SmsCodeAuthenticationToken;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.List;

/**
 * description: UserController
 * date: 2020/7/29
 * author: lijiaxi-os
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }

    @Autowired
    private AuthenticationManager authenticationManager;


    @Resource
    private ObjectMapper objectMapper;


    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();


    @Resource
    private DefaultTokenServices tokenService;

    @Autowired
    private UserService userService;

    @Resource
    private DataSource dataSource;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/demo")
    public String demo() {
        return "ok";
    }

    @GetMapping("/acs_demo")
    public String demoAcs() {
        return "need_auth";
    }

    @PostMapping("/login/image")
    public void login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientId = request.getHeader("client_id");
        String clientSecret = request.getHeader("client_secret");
        if (clientId == null || "".equals(clientId)) {
            throw new UnapprovedClientAuthenticationException("请求头中无client_id信息");
        }

        if (clientSecret == null || "".equals(clientSecret)) {
            throw new UnapprovedClientAuthenticationException("请求头中无client_secret信息");
        }

        ClientDetails clientDetails = getClient(clientId, clientSecret);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //ClientDetails clientDetails = getClient("browser", "clientSecret");
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "customer");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = tokenService.createAccessToken(oAuth2Authentication);
        oAuth2Authentication.setAuthenticated(true);

        writerObj(response, oAuth2AccessToken);
    }


    private void writerObj(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(objectMapper.writeValueAsString(obj));
            writer.flush();
        }
    }


    private ClientDetails getClient(String clientId, String clientSecret) {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource, redisTemplate, objectMapper);
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的信息不存在");
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配");
        }
        return clientDetails;
    }

    @PostMapping("/addUser")
    public BaseResponse addUser(@RequestBody User user) {
        userService.insert(user);
        return BaseResponse.Ok();
    }

    @PostMapping("/login/sms")
    public void loginByPhone(String phone, String code,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(phone);
        writerToken(request, response, token, "手机号或密码错误", 1L);
    }

    private void writerToken(HttpServletRequest request, HttpServletResponse response, AbstractAuthenticationToken token
            , String badCredenbtialsMsg, Long userId) throws IOException {
        try {
            String clientId = request.getHeader("client_id");
            String clientSecret = request.getHeader("client_secret");
            if (clientId == null || "".equals(clientId)) {
                throw new UnapprovedClientAuthenticationException("请求头中无client_id信息");
            }

            if (clientSecret == null || "".equals(clientSecret)) {
                throw new UnapprovedClientAuthenticationException("请求头中无client_secret信息");
            }

            ClientDetails clientDetails = getClient(clientId, clientSecret);
            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "customer");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken oAuth2AccessToken = tokenService.createAccessToken(oAuth2Authentication);
            oAuth2Authentication.setAuthenticated(true);

            writerObj(response, oAuth2AccessToken);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/list")
    public BaseResponse<List<User>> list() {
        return BaseResponse.Ok().putData(userService.list());
    }
}
