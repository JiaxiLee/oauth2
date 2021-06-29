package com.li.oauth.gateway.dao;

import com.li.oauth.gateway.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description: MenuDao
 * date: 2021/5/31
 * author: lijiaxi-os
 */
@Repository
@Mapper
public interface MenuDao {
    Menu findByUrl(String url);
}
