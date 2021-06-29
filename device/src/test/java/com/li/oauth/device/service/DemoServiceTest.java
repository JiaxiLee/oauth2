package com.li.oauth.device.service;

import com.li.oauth.device.client.AuthServiceClient;
import com.li.oauth.device.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static org.junit.Assert.*;

/**
 * description: DemoServiceTest
 * date: 2021/6/24
 * author: lijiaxi-os
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//@EnableFeignClients(clients = DemoServiceTest.AuthServiceClient.class)
public class DemoServiceTest {

    /*@FeignClient(name = "auth-service")
    public interface AuthServiceClient {
        @RequestMapping(method = RequestMethod.GET, value = "/uaa/users/list", consumes = MediaType.APPLICATION_JSON_VALUE)
        List<User> list();
    }*/

    @Autowired
    private AuthServiceClient demoService;

    @Test
    public void test1() {
        demoService.list();
    }
}