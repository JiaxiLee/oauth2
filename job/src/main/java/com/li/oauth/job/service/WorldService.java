package com.li.oauth.job.service;

import com.li.oauth.job.client.HelloServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: WorldService
 * date: 2020/7/30
 * author: lijiaxi-os
 */
@Service
public class WorldService {


    @Autowired
    private HelloServiceClient helloServiceClient;

    public String testFeign() {
        return helloServiceClient.test();
    }

}
