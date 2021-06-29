package com.li.oauth.auth.controller;

import com.google.code.kaptcha.Producer;
import com.li.oauth.auth.constant.SecurityConstants;
import com.li.oauth.auth.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * description: ValidateCodeController
 * date: 2021/5/30
 * author: lijiaxi-os
 */
@RestController
@RequestMapping("/code")
@Slf4j
public class ValidateCodeController {

    @Autowired
    private Producer producer;

    @Autowired
    private SmsService smsService;

    @GetMapping("/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        log.info("生成的图片验证码是：" + text);
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        request.getSession().setAttribute(SecurityConstants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
    }

    @GetMapping("/sms")
    public BaseResponse smsCode(@RequestParam("phone") String phone) {
        smsService.sendSms(phone);
        return BaseResponse.Ok();
    }

}
