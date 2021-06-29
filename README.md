## oauth2实现单点登录及权限控制 ##

本项目采用的springcloud框架。采用security 和 oauth2 作为登录鉴权框架。项目架构如下：

	oauth
	└── auth-service -- 消息服务模块 
	     ├── common -- 通用包
	     ├── config-- 配置包
	     ├── constant-- 常量包  
	     ├── controller-- 控制层包 
	     ├── dao-- 存储层包 
	     ├── dto-- 对象传输层包
		 ├── exception-- 异常定义包
		 ├── image-- 图形验证码登录包
		 ├── sms-- 短信验证码登录包
		 ├── properties-- 属性包
		 ├── security-- 安全包
	     ├── entity-- 数据库对象映射实体类
	     ├── enums-- 枚举包  
	     ├── service-- 业务实现包
	     └── utils-- 工具包
	└── base-service -- 基础模块
	└── eurake-service -- 服务注册中心
	└── gateway-service -- 网关模块
	└── device-service -- 网关模块
	     ├── common -- 通用包
	     ├── config-- 配置包
	     ├── constant-- 常量包  
	     ├── controller-- 控制层包 
	     ├── dao-- 存储层包 
	     ├── dto-- 对象传输层包
		 ├── exception-- 异常定义包
	     ├── entity-- 数据库对象映射实体类
	     ├── enums-- 枚举包  
	     ├── service-- 业务实现包
	     └── utils-- 工具包  

主要有五个模块 eurake 服务注册中心，base 基础模块，auth 授权认证模块， gateway 网关登录模块， device 设备模块（资源模块）.

流程如下：

![](http://qiniu.lijiaxi.com.cn/login1.png) 

用户角色权限主要涉及这几个表：
	
	-- 用户表
	drop table if exists `u_user`;
	CREATE TABLE `u_user` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
	  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
	  `password` varchar(50) DEFAULT NULL,
	  `phone` varchar(24) Default null comment '手机号',
	  `role_id` INT(10) NOT NULL DEFAULT 0 Comment '角色id',
	  `pic_path` varchar(200) DEFAULT '/images/logo.png' COMMENT '头像地址',
	  `status` int(2) DEFAULT 0 comment '锁定状态',
	  `sessionId` varchar(50) DEFAULT NULL,
	  `create_time` datetime DEFAULT NULL,
	  PRIMARY KEY (`id`)
	);

	-- 角色表
	drop table if exists `u_role`;
	CREATE TABLE `u_role` (
	  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
	  `name` varchar(64)   DEFAULT NULL COMMENT '角色',
	  `role_name` varchar(64)   DEFAULT NULL COMMENT '角色名称',
	  `role_des` varchar(64)   DEFAULT NULL COMMENT '角色描述',
	  `company_id` int(10) DEFAULT NULL COMMENT '公司id',
	  `create_date` int(10) DEFAULT NULL COMMENT '创建时间',
	  `update_date` int(10) DEFAULT NULL COMMENT '更新时间',
	  `status` int(2)  DEFAULT '0' COMMENT '数据状态',
	  PRIMARY KEY (`id`)
	);
	drop table if exists `u_menu`;
	CREATE TABLE `u_menu` (
	  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
	  `menu_code` INT(12) NOT NULL DEFAULT 0 Comment '菜单编码',
	  `m_name` varchar(128)   DEFAULT NULL COMMENT '菜单名称',
	  `m_parent_id` int(10) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
	  `m_url` varchar(256)   DEFAULT NULL COMMENT '菜单URL,类型：1.普通页面（如用户管理， /mgr/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
	  `m_perms` varchar(512)   DEFAULT NULL COMMENT '授权的权限',
	  `m_type` int(11) DEFAULT NULL COMMENT '0：目录 1：菜单 2：按钮',
	  `m_order_num` int(11) DEFAULT NULL COMMENT '排序',
	  `create_date` int(10) DEFAULT NULL COMMENT '创建时间',
	  `update_date` int(10) DEFAULT NULL COMMENT '更新时间',
	  `status` int(2)   DEFAULT '0' COMMENT '数据状态',
	  `icon` varchar(32)   DEFAULT NULL COMMENT '菜單圖標',
	  `name` varchar(64)   DEFAULT NULL COMMENT '菜單英文名稱',
	  `m_default` int(12) DEFAULT NULL COMMENT '默认菜单 0表示不显示 1表示默认显示',
	  PRIMARY KEY (`id`)
	);

	drop table if exists `u_role_menu`;
	CREATE TABLE `u_role_menu` (
	  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
	  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
	  `menu_id` int(10) DEFAULT NULL COMMENT '菜单id',
	  `create_date` int(10) DEFAULT NULL COMMENT '创建时间',
	  `update_date` int(10) DEFAULT NULL COMMENT '更新时间',
	  `status` int(2)   DEFAULT '0' COMMENT '数据状态',
	  PRIMARY KEY (`id`)
	);

	DROP TABLE IF EXISTS `oauth_client_details`;
	CREATE TABLE `oauth_client_details` (
	  `client_id` varchar(32) NOT NULL,
	  `resource_ids` varchar(256) DEFAULT NULL,
	  `client_secret` varchar(256) DEFAULT NULL,
	  `scope` varchar(256) DEFAULT NULL,
	  `authorized_grant_types` varchar(256) DEFAULT NULL,
	  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
	  `authorities` varchar(256) DEFAULT NULL,
	  `access_token_validity` int(11) DEFAULT NULL,
	  `refresh_token_validity` int(11) DEFAULT NULL,
	  `additional_information` varchar(4096) DEFAULT NULL,
	  `autoapprove` varchar(256) DEFAULT NULL,
	  PRIMARY KEY (`client_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='资源信息表';
用户登录后会生成一个token，token映射到该用户所拥有的权限，及访问资源的权限。

用户访问任何资源时，第一步经过gateway，gateway做菜单角色路径拦截判断。主要复写`AccessDecisionManager`,`FilterInvocationSecurityMetadataSource`,`AbstractSecurityInterceptor`可以实现判断该用户是否有访问某一菜单的权限。

为了跨服务间token有效，因此使用了redis来存储token。

### 更新日志 ###
· 引入feign 跨组件间调用，并使用oauth 进行鉴权和授权 2021-06-29  

