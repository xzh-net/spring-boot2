package net.xzh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data  // Lombok注解
@TableName("user")  // 指定表名
public class User {
    @TableId(type = IdType.AUTO)  // 主键自增
    private Long id;
    private String name;
    private Integer age;
    private String email;
}