package net.xzh.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 搜索中的商品属性信息
 * 
 * @author xzh Created 2018/6/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsProductAttributeValue {

	/**
	 * 属性ID
	 */
	@Field(type = FieldType.Long)
	private Long productAttributeId;

	/**
	 * 属性名称
	 */
	@Field(type = FieldType.Keyword)
	private String name;

	/**
	 * 属性值
	 */
	@Field(type = FieldType.Keyword)
	private String value;

	/**
	 * 属性值ID
	 */
	@Field(type = FieldType.Long)
	private Long id;

	/**
	 * 属性类型：0->规格；1->参数
	 */
	@Field(type = FieldType.Integer)
	private Integer type;
}