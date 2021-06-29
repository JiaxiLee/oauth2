package com.li.oauth.auth.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class Role implements Serializable {

	private static final long serialVersionUID =  8234051142220830746L;

	/**
	 * 数据库id
	 */
	private Long id;

	/**
	 * 角色
	 */
	private String name;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleDes;

	/**
	 * 公司id
	 */
	private Long companyId;

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
