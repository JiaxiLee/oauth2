package com.li.oauth.auth.dao;

import com.li.oauth.base.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: UserDao
 * date: 2020/10/10
 * author: lijiaxi-os
 */
@Mapper
@Repository
public interface UserDao {
    User findByUsername(String username);

    User findByPhone(String phone);

    int insert(User user);

    List<User> list();
}
