package net.xzh.datasource.component;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import net.xzh.datasource.model.DynamicDataSourceConfig;
import net.xzh.datasource.service.DynamicDataSourceConfigService;

@Service
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dynamic")
public class DataSourceManageService {
    
    @Autowired
    private DynamicDataSourceManager dataSourceManager;
    
    @Autowired
    private DataSourceBuilder dataSourceBuilder;
    
    @Autowired
    private DynamicDataSourceConfigService configService;
    
    /**
     * 添加新数据源
     */
    public boolean addDataSource(String dataSourceKey, String dataSourceName,
                               String host, String port, String dataBaseName,
                               String username, String password) {
        // 构建配置对象
        DynamicDataSourceConfig config = dataSourceBuilder.buildConfig(
        		dataSourceKey, dataSourceName, host, port, dataBaseName, username, password);
        return dataSourceManager.addDataSource(config);
    }
    
    /**
     * 移除数据源
     */
    public boolean removeDataSource(String dataSourceKey) {
        return dataSourceManager.removeDataSource(dataSourceKey);
    }
    
    /**
     * 获取所有数据源
     */
    public Set<String> getAllDataSources() {
        return dataSourceManager.getDataSourceKeys();
    }
    
    /**
     * 测试数据源连接
     */
    public boolean testDataSource(String host, String port, String databaseName,
                                String username, String password) {
        String dataSourceKey = host + "_" + port + "_" + databaseName;
        DynamicDataSourceConfig config = dataSourceBuilder.buildConfig(
        		dataSourceKey, "测试数据源", host, port, databaseName, username, password);
        
        return dataSourceManager.testDataSource(config);
    }
    
    /**
     * 获取所有数据源配置
     */
    public List<DynamicDataSourceConfig> getAllDataSourceConfigs() {
        return configService.getActiveConfigs();
    }
}