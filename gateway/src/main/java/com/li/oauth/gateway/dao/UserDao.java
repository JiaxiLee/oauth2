package com.li.oauth.gateway.dao;

import com.li.oauth.gateway.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}
