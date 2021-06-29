package com.li.oauth.device.client;

import com.li.oauth.device.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * description: AuthServiceClient
 * date: 2021/6/28
 * author: lijiaxi-os
 */
@FeignClient(name = "auth-service")
public interface AuthServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/uaa/users/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<User> list();
}
