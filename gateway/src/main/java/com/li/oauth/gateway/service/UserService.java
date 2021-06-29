package com.li.oauth.gateway.service;

import com.li.oauth.gateway.common.RtnEnum;
import com.li.oauth.gateway.dao.RoleMenuDao;
import com.li.oauth.gateway.dao.UserDao;
import com.li.oauth.gateway.entity.Menu;
import com.li.oauth.gateway.entity.User;
import com.li.oauth.gateway.exception.InsertUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * description: UserService
 * date: 2021/5/31
 * author: lijiaxi-os
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    public List<Menu> findByRoleId(Integer roleId) {
        return roleMenuDao.findByRoleId(roleId);
    }

    public void insert(User user) {
        User ex = findByUsername(user.getUsername());
        if (null != ex) {
            throw new InsertUserException(RtnEnum.USER_NAME_EXIST);
        }
        ex = findByPhone(user.getPhone());
        if (ex != null) {
            throw new InsertUserException(RtnEnum.USER_PHONE_EXIST);
        }

        user.setCreateTime(new Date());
        userDao.insert(user);

    }

}
