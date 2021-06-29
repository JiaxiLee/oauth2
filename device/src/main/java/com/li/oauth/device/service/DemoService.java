package com.li.oauth.device.service;

import com.li.oauth.device.client.AuthServiceClient;
import com.li.oauth.device.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description: DemoService
 * date: 2021/6/24
 * author: lijiaxi-os
 */
@Service
public class DemoService {
    @Autowired
    private AuthServiceClient authServiceClient;


    public void list() {
        List<User> users = authServiceClient.list();
        users.forEach(e -> System.out.println(e));
    }
}
