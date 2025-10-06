package net.xzh.datasource.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author CR7
 *
 */
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dynamic")
public class DynamicDataSource extends AbstractRoutingDataSource {
    
    private final Map<Object, Object> backupTargetDataSources = new ConcurrentHashMap<>();
    
    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSources) {
        this.backupTargetDataSources.putAll(targetDataSources);
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(new ConcurrentHashMap<>(targetDataSources));
        super.afterPropertiesSet();
    }
    
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
    
    /**
     * 获取当前所有数据源
     */
    public Map<Object, Object> getCurrentDataSources() {
        return new ConcurrentHashMap<>(backupTargetDataSources);
    }
    
    /**
     * 更新数据源
     */
    public synchronized void updateDataSources(Map<Object, Object> newDataSources) {
        this.backupTargetDataSources.clear();
        this.backupTargetDataSources.putAll(newDataSources);
        super.setTargetDataSources(new ConcurrentHashMap<>(newDataSources));
        super.afterPropertiesSet();
    }
}