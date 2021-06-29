package com.li.oauth.job.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * description: HelloServiceClient
 * date: 2020/7/30
 * author: lijiaxi-os
 */
@FeignClient(name = "hello-service")
public interface HelloServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/hello/test1")
    String test();
}

