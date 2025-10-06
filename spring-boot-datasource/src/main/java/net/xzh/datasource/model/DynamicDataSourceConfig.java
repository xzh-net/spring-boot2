package net.xzh.datasource.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 动态数据源表
 * 
 * @author CR7
 *
 */
@Data
@TableName("dynamic_datasource_config")
public class DynamicDataSourceConfig {

	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 数据源标识键
	 */
	@TableField("datasource_key")
	private String dataSourceKey;

	/**
	 * 数据源名称
	 */
	@TableField("datasource_name")
	private String dataSourceName;

	/**
	 * 数据库名
	 */
	@TableField("database_name")
	private String dataBaseName;

	/**
	 * 数据库URL
	 */
	@TableField("url")
	private String url;

	/**
	 * 用户名
	 */
	@TableField("username")
	private String username;

	/**
	 * 密码
	 */
	@TableField("password")
	private String password;

	/**
	 * 驱动类名
	 */
	@TableField("driver_class_name")
	private String driverClassName;

	/**
	 * 主机
	 */
	@TableField("host")
	private String host;

	/**
	 * 端口
	 */
	@TableField("port")
	private String port;

	/**
	 * 是否激活
	 */
	@TableField("is_active")
	private Boolean isActive;
}