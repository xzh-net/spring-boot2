package net.xzh.datasource.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import net.xzh.datasource.model.DynamicDataSourceConfig;
import net.xzh.datasource.service.DynamicDataSourceConfigService;

/**
 * 动态数据源管理器
 * @author CR7
 *
 */
@Component
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dynamic")
public class DynamicDataSourceManager {
    
    @Autowired
    private DynamicDataSource dynamicDataSource;
    
    @Autowired
    private DynamicDataSourceConfigService dynamicDataSourceConfigService;
    
    @Autowired
    private DataSourceBuilder dataSourceBuilder;
    
    private volatile boolean initialized = false;
    
    /**
     * 初始化所有数据源（从数据库加载配置）
     */
    @PostConstruct
    public void initializeDataSources() {
        if (initialized) {
            return;
        }
        try {
            List<DynamicDataSourceConfig> configs = dynamicDataSourceConfigService.getActiveConfigs();
            Map<Object, Object> dataSourceMap = new HashMap<>();
            
            // 添加默认数据源（如果存在）
            Map<Object, Object> currentDataSources = dynamicDataSource.getCurrentDataSources();
            if (currentDataSources.containsKey("primary")) {
                dataSourceMap.put("primary", currentDataSources.get("primary"));
            }
            
            for (DynamicDataSourceConfig config : configs) {
                // 跳过已经在当前数据源中的配置
                if ("primary".equals(config.getDataSourceKey()) || 
                    dataSourceMap.containsKey(config.getDataSourceKey())) {
                    continue;
                }
                
                try {
                    DataSource dataSource = dataSourceBuilder.buildDataSource(config);
                    dataSourceMap.put(config.getDataSourceKey(), dataSource);
                    System.out.println("初始化数据源: " + config.getDataSourceKey());
                } catch (Exception e) {
                    System.err.println("初始化数据源失败: " + config.getDataSourceKey() + ", 错误: " + e.getMessage());
                }
            }
            
            dynamicDataSource.updateDataSources(dataSourceMap);
            initialized = true;
            
            System.out.println("动态数据源初始化完成，共加载 " + dataSourceMap.size() + " 个数据源");
        } catch (Exception e) {
            System.err.println("动态数据源初始化失败: " + e.getMessage());
            throw new RuntimeException("动态数据源初始化失败", e);
        }
    }
    
    /**
     * 添加数据源
     */
    public synchronized boolean addDataSource(DynamicDataSourceConfig config) {
        try {
            // 保存配置到数据库
            config.setIsActive(true);
            if (!dynamicDataSourceConfigService.save(config)) {
                throw new RuntimeException("保存数据源配置失败");
            }
            
            // 构建数据源并测试连接
            DataSource dataSource = dataSourceBuilder.buildDataSource(config);
            dataSourceBuilder.testConnection(dataSource);
            
            // 添加到动态数据源
            Map<Object, Object> currentDataSources = dynamicDataSource.getCurrentDataSources();
            currentDataSources.put(config.getDataSourceKey(), dataSource);
            dynamicDataSource.updateDataSources(currentDataSources);
            
            return true;
        } catch (Exception e) {
            // 回滚：删除数据库配置
        	dynamicDataSourceConfigService.remove(config.getDataSourceKey());
            throw new RuntimeException("添加数据源失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 移除数据源
     */
    public synchronized boolean removeDataSource(String datasourceKey) {
        if ("primary".equals(datasourceKey)) {
            throw new RuntimeException("不能移除主数据源");
        }
        
        try {
            // 从数据库软删除配置
            if (!dynamicDataSourceConfigService.remove(datasourceKey)) {
                throw new RuntimeException("数据源配置不存在: " + datasourceKey);
            }
            
            // 从动态数据源中移除
            Map<Object, Object> currentDataSources = dynamicDataSource.getCurrentDataSources();
            if (currentDataSources.containsKey(datasourceKey)) {
                // 关闭数据源连接
                DataSource dataSource = (DataSource) currentDataSources.get(datasourceKey);
                if (dataSource instanceof HikariDataSource) {
                    ((HikariDataSource) dataSource).close();
                }
                
                currentDataSources.remove(datasourceKey);
                dynamicDataSource.updateDataSources(currentDataSources);
            }
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException("移除数据源失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 获取所有数据源键
     */
    public Set<String> getDataSourceKeys() {
        return dynamicDataSource.getCurrentDataSources().keySet()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }
    
    /**
     * 检查数据源是否存在
     */
    public boolean containsDataSource(String datasourceKey) {
        return dynamicDataSource.getCurrentDataSources().containsKey(datasourceKey);
    }
    
    /**
     * 测试数据源连接
     */
    public boolean testDataSource(DynamicDataSourceConfig config) {
        try {
            DataSource dataSource = dataSourceBuilder.buildDataSource(config);
            dataSourceBuilder.testConnection(dataSource);
            
            // 关闭测试连接
            if (dataSource instanceof HikariDataSource) {
                ((HikariDataSource) dataSource).close();
            }
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}