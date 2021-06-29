package com.li.oauth.job.client;

import com.li.oauth.base.constant.BaseResponse;
import com.li.oauth.base.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * description: UserServiceClient
 * date: 2021/6/28
 * author: lijiaxi-os
 */
@FeignClient(name = "auth-service")
public interface UserServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/uaa/users/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse<List<User>> list();

    @RequestMapping(method = RequestMethod.GET, value = "/uaa/code/image")
    String imageCode();
}
