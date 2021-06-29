package com.li.oauth.auth.security;

import com.li.oauth.auth.entity.Menu;
import com.li.oauth.auth.service.UserService;
import com.li.oauth.base.entity.User;
import com.li.oauth.base.security.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * description: InUserDetailService
 * date: 2020/7/29
 * author: lijiaxi-os
 */
@Service
@Slf4j
public class InUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("login username is {}", s);
        User user = userService.findByUsername(s);
        log.info("User " + user);
        // 从数据库拉取密文密码进行userdetail赋值
        if (user != null) {
            List<Menu> menus = userService.findByRoleId(user.getRoleId());
            List<Integer> menuCodes = menus.stream().map(Menu::getMenuCode).collect(Collectors.toList());
            return new UserDetail(s, user.getPassword(), menuCodes);
        } else {
            throw new UsernameNotFoundException(s);
        }
    }

    public UserDetails loadUserByPhone(String s) throws UsernameNotFoundException {
        log.info("login username is {}", s);
        User user = userService.findByPhone(s);
        log.info("User " + user);
        // 从数据库拉取密文密码进行userdetail赋值
        if (user != null) {
            List<Menu> menus = userService.findByRoleId(user.getRoleId());
            List<Integer> menuCodes = menus.stream().map(Menu::getMenuCode).collect(Collectors.toList());
            return new UserDetail(s, user.getPassword(), menuCodes);
        } else {
            throw new UsernameNotFoundException(s);
        }
    }


}
