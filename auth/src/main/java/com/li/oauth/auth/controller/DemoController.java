package com.li.oauth.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: DemoController
 * date: 2021/6/22
 * author: lijiaxi-os
 */
@RestController
@RequestMapping("/need")
public class DemoController {

    @GetMapping("/ok")
    public String demo() {
        return "ok";
    }
}
