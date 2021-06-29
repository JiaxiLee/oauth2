package com.li.oauth.base.dao;

import com.li.oauth.gateway.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description: UserDaoTest
 * date: 2021/6/24
 * author: lijiaxi-os
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsername() {
        System.out.println(userDao.findByPhone("111"));
    }
}