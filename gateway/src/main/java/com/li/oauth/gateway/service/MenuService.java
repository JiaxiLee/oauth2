package com.li.oauth.gateway.service;

import com.li.oauth.gateway.dao.MenuDao;
import com.li.oauth.gateway.entity.Menu;
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
    private HellService hellService;

    @Autowired(required = false)
    private MenuDao menuDao;

    public Menu findByUrl(String url) {
        return menuDao.findByUrl(url);
    }
}
