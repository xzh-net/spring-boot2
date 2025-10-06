package net.xzh.datasource.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 用户信息
 * @author xzh
 * @since 2024-05-13
 */

@Data
@TableName("user")
public class User {
	
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private Integer age;
    private String email;

}
