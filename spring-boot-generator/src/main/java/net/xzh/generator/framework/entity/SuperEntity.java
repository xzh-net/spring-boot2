package net.xzh.generator.framework.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 持久层PO多业务字段通用实体
 * 
 * @author xzh
 *
 * @param <T> 泛型类型，必须继承自Model
 */
@Setter
@Getter
public class SuperEntity<T extends Model<?>> extends BaseSuperEntity<T> {

    /**
     *
     */
    private static final long serialVersionUID = 6878083228753234679L;
    
    /**
     * 删除标志（0正常 1删除）
     */
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
    
    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;
    
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    
    /**
     * 更新人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    
    
}
