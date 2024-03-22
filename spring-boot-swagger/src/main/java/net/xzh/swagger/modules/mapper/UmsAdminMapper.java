package net.xzh.swagger.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.xzh.swagger.modules.model.UmsAdmin;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);

}
