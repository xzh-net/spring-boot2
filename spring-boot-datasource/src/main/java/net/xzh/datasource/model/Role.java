package net.xzh.datasource.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 角色管理
 * @author xzh
 * @since 2024-05-13
 */

@Data
@TableName("role")
public class Role {
	
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    
    /**
     * 状态（0正常 1禁用 ）
     */
    @TableField("status")
    private Integer status;
}
