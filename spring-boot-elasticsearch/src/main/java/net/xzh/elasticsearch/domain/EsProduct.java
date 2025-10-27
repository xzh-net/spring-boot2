package net.xzh.elasticsearch.domain;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 搜索中的商品信息
 * 
 * @author xzh
 * Created 2018/6/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product")
public class EsProduct {

    @Id
    private Long id;

    /**
     * 商品号
     */
    @Field(type = FieldType.Keyword)
    private String productSn;

    /**
     * 商品名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;

    /**
     * 副标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String subTitle;

    /**
     * 关键字
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String keywords;

    /**
     * 销售价
     */
    @Field(type = FieldType.Scaled_Float, scalingFactor = 10000)
    private BigDecimal price;

    /**
     * 销量
     */
    @Field(type = FieldType.Integer)
    private Integer sale;

    /**
     * 品牌ID
     */
    @Field(type = FieldType.Keyword)
    private Long brandId;

    /**
     * 品牌名称
     */
    @Field(type = FieldType.Keyword)
    private String brandName;

    /**
     * 分类ID
     */
    @Field(type = FieldType.Keyword)
    private Long productCategoryId;

    /**
     * 分类名称
     */
    @Field(type = FieldType.Keyword)
    private String productCategoryName;

    /**
     * 商品图片
     */
    @Field(type = FieldType.Keyword)
    private String pic;

    /**
     * 新品状态：0->不是新品；1->新品
     */
    @Field(type = FieldType.Integer)
    private Integer newStatus;

    /**
     * 推荐状态：0->不推荐；1->推荐
     */
    @Field(type = FieldType.Integer)
    private Integer recommandStatus;

    /**
     * 库存
     */
    @Field(type = FieldType.Integer)
    private Integer stock;

    /**
     * 促销类型：0->没有促销使用原价;1->使用促销价
     */
    @Field(type = FieldType.Integer)
    private Integer promotionType;

    /**
     * 排序
     */
    @Field(type = FieldType.Integer)
    private Integer sort;

    /**
     * 参数规格（嵌套）
     */
    @Field(type = FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;
}