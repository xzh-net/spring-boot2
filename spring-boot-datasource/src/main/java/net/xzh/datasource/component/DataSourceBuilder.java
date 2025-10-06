package net.xzh.datasource.component;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import net.xzh.datasource.model.DynamicDataSourceConfig;

/**
 * 数据源构建器
 * @author CR7
 *
 */

@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dynamic")
@Component
public class DataSourceBuilder {
    
    /**
     * 构建数据源
     */
    public DataSource buildDataSource(DynamicDataSourceConfig config) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(config.getUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setMaximumPoolSize(20);
        dataSource.setMinimumIdle(5);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);
        dataSource.setMaxLifetime(1800000);
        dataSource.setPoolName("HikariPool-" + config.getDataSourceKey());
        return dataSource;
    }
    
    /**
     * 构建数据源配置对象
     */
    public DynamicDataSourceConfig buildConfig(String dataSourceKey, String dataSourceName,
                                             String host, String port, String dataBaseName,
                                             String username, String password) {
        String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai", 
                host, port, dataBaseName);
        
        DynamicDataSourceConfig config = new DynamicDataSourceConfig();
        config.setDataSourceKey(dataSourceKey);
        config.setDataSourceName(dataSourceName);
        config.setUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setHost(host);
        config.setPort(port);
        config.setDataBaseName(dataBaseName);
        config.setIsActive(true);
        
        return config;
    }
    
    /**
     * 测试数据源连接
     */
    public void testConnection(DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            if (!conn.isValid(1000)) {
                throw new RuntimeException("数据源连接测试失败");
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据源连接测试失败: " + e.getMessage(), e);
        }
    }
}