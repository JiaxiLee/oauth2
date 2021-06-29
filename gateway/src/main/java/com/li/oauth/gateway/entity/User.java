package com.li.oauth.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class User implements Serializable {

	private static final long serialVersionUID =  1656187409993216954L;

	private Integer id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 昵称
	 */
	private String nickName;

	private String password;

	/**
	 * 手机号
	 */
	private String phone;

	private Integer roleId;

	/**
	 * 头像地址
	 */
	private String picPath;

	/**
	 * 锁定状态
	 */
	private Integer status;

	private String sessionId;

	private Date createTime;

}
