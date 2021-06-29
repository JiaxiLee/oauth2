package com.li.oauth.auth.service;

import com.li.oauth.auth.constant.SecurityConstants;
import com.li.oauth.auth.sms.SmsCodeSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * description: SmsService
 * date: 2021/6/1
 * author: lijiaxi-os
 */
@Service
public class SmsService {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sendSms(String phone) {
        // 生成验证码
        String code = RandomStringUtils.randomNumeric(SecurityConstants.SMS_CODE_LENGTH);
        // 存储验证码
        stringRedisTemplate.opsForValue().set(phone, code, 60, TimeUnit.SECONDS);
        // 发送验证码
        smsCodeSender.send(phone, code);
    }
}
