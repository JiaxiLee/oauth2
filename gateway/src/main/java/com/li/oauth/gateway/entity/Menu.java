package com.li.oauth.gateway.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class Menu implements Serializable {

	private static final long serialVersionUID =  5718734573324910657L;

	/**
	 * 数据库id
	 */
	private Integer id;

	private Integer menuCode;

	/**
	 * 菜单名称
	 */
	private String mName;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long mParentId;

	/**
	 * 菜单URL,类型：1.普通页面（如用户管理， /mgr/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)
	 */
	private String mUrl;

	/**
	 * 授权的权限
	 */
	private String mPerms;

	/**
	 * 0：目录 1：菜单 2：按钮
	 */
	private Integer mType;

	/**
	 * 排序
	 */
	private Integer mOrderNum;

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

	/**
	 * 菜單圖標
	 */
	private String icon;

	/**
	 * 菜單英文名稱
	 */
	private String name;

	/**
	 * 默认菜单 0表示不显示 1表示默认显示
	 */
	private Integer mDefault;

}
