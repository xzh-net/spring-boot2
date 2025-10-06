package net.xzh.datasource.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.datasource.mapper.DynamicDataSourceConfigMapper;
import net.xzh.datasource.model.DynamicDataSourceConfig;
import net.xzh.datasource.service.DynamicDataSourceConfigService;

@Service
public class DynamicDataSourceConfigServiceImpl extends ServiceImpl<DynamicDataSourceConfigMapper, DynamicDataSourceConfig> implements DynamicDataSourceConfigService {
    
	@Resource
    private DynamicDataSourceConfigMapper configMapper;
    
    /**
     * 获取所有激活的数据源配置
     */
    public List<DynamicDataSourceConfig> getActiveConfigs() {
        return configMapper.selectActiveConfigs();
    }
    
    /**
     * 根据键获取数据源配置
     */
    public DynamicDataSourceConfig getByKey(String datasourceKey) {
        return configMapper.selectByKey(datasourceKey);
    }
    
    /**
     * 保存数据源配置
     */
    public boolean save(DynamicDataSourceConfig config) {
        DynamicDataSourceConfig existing = getByKey(config.getDataSourceKey());
        if (existing != null) {
            config.setId(existing.getId());
            return configMapper.updateById(config) > 0;
        } else {
            return configMapper.insert(config) > 0;
        }
    }
    
    /**
     * 删除数据源配置
     */
    public boolean remove(String datasourceKey) {
        if ("primary".equals(datasourceKey)) {
            throw new RuntimeException("不能删除主数据源配置");
        }
        
        LambdaUpdateWrapper<DynamicDataSourceConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(DynamicDataSourceConfig::getIsActive, false)
               .eq(DynamicDataSourceConfig::getDataSourceKey, datasourceKey);
        return configMapper.update(null, wrapper) > 0;
    }
    
    /**
     * 检查数据源配置是否存在
     */
    public boolean exists(String datasourceKey) {
        return getByKey(datasourceKey) != null;
    }
}