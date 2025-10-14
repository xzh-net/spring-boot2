package net.xzh.generator.model.vo.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页对象
 * 
 * @author xzh
 */
@Data
@Schema(description = "分页对象")
public abstract class BasePageVO {

	/**
	 * 页码
	 */
	@Schema(description = "页码")
	private Integer pageNum = 1; // 默认页码为1

	/**
	 * 每页数量
	 */
	@Schema(description = "每页数量")
	private Integer pageSize = 10; // 默认每页显示10条数据

}
