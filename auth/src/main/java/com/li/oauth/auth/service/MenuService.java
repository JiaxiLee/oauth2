package com.li.oauth.auth.service;

import com.li.oauth.auth.dao.MenuDao;
import com.li.oauth.auth.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: MenuService
 * date: 2021/5/31
 * author: lijiaxi-os
 */
@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public Menu findByUrl(String url) {
        return menuDao.findByUrl(url);
    }
}
