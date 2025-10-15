package net.xzh.log.model;

import java.util.Date;

import lombok.Data;

/**
 * 操作日志记录
 */
@Data
public class Audit {

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 模块标题
	 */
	private String title;

	/**
	 * 业务类型（0其它 1查询 2新增 3修改 4删除 5上传 6下载 7导出 8导入 9强退）
	 */
	private Integer businessType;

	/**
	 * 方法名称
	 */
	private String method;

	/**
	 * 请求方式
	 */
	private String requestMethod;

	/**
	 * 操作类别（0其它 1后台用户 2手机端用户）
	 */
	private Integer operatorType;

	/**
	 * 请求URL
	 */
	private String operUrl;

	/**
	 * 主机地址
	 */
	private String operIp;

	/**
	 * 操作地点
	 */
	private String operLocation;

	/**
	 * 请求参数
	 */
	private String operParam;

	/**
	 * 返回参数
	 */
	private String jsonResult;

	/**
	 * 操作状态（0正常 1异常）
	 */
	private Integer status;

	/**
	 * 错误消息
	 */
	private String errorMsg;

	/**
	 * 消耗时间
	 */
	private Long costTime;
	/**
	 * 创建人id
	 */
	private Long createUserId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 操作人
	 */
	private Long deptId;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 操作人
	 */
	private String operUsername;
}
