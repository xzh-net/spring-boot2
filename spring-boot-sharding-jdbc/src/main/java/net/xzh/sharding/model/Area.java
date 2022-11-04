package net.xzh.sharding.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xzh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("area")
public class Area extends SuperEntity {
	private static final long serialVersionUID = 8898492657846787286L;
	private String name;
}
