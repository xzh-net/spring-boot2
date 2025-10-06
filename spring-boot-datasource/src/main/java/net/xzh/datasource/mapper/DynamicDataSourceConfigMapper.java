package net.xzh.datasource.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.xzh.datasource.model.DynamicDataSourceConfig;

/**
 * 动态数据源管理操作
 * 
 * @author CR7
 *
 */
public interface DynamicDataSourceConfigMapper extends BaseMapper<DynamicDataSourceConfig> {

	/**
	 * 查询所有激活的数据源配置
	 */
	@Select("select * from dynamic_datasource_config where is_active = 1")
	List<DynamicDataSourceConfig> selectActiveConfigs();

	/**
	 * 根据数据源键查询
	 */
	@Select("select * from dynamic_datasource_config where datasource_key = #{datasourceKey}")
	DynamicDataSourceConfig selectByKey(String datasourceKey);
}