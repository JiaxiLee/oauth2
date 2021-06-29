package com.li.oauth.auth.dao;

import com.li.oauth.auth.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: RoleMenuDao
 * date: 2021/5/31
 * author: lijiaxi-os
 */
@Mapper
@Repository
public interface RoleMenuDao {
    List<Menu> findByRoleId(Integer roleId);
}
