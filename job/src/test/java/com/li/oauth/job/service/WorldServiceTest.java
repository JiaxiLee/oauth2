package com.li.oauth.job.service;

import com.li.oauth.job.client.HelloServiceClient;
import com.li.oauth.job.client.UserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * description: WorldServiceTest
 * date: 2021/6/28
 * author: lijiaxi-os
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WorldServiceTest {



    @Autowired
    private HelloServiceClient helloServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;
    @org.junit.Test
    public void testFeign() {
        helloServiceClient.test();
    }

    @Test
    public void auth() {
        userServiceClient.list();
    }

    @Test
    public void imageCode() {
        userServiceClient.imageCode();
    }
}