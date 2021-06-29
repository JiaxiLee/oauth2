package com.li.oauth.gateway.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class RoleMenu implements Serializable {

	private static final long serialVersionUID =  4947738643230815246L;

	/**
	 * 数据库id
	 */
	private Long id;

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 菜单id
	 */
	private Long menuId;

	/**
	 * 创建时间
	 */
	private Long createDate;

	/**
	 * 更新时间
	 */
	private Long updateDate;

	/**
	 * 数据状态
	 */
	private Integer status;

}
