package com.li.oauth.device.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: DeviceController
 * date: 2021/6/25
 * author: lijiaxi-os
 */
@RestController
public class DeviceController {

    @GetMapping("demo")
    public String demo() {
        return "ok";
    }
}
