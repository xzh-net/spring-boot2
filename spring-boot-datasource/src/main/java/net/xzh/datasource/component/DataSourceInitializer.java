package net.xzh.datasource.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dynamic")
public class DataSourceInitializer implements ApplicationListener<ApplicationReadyEvent> {
    
    @Autowired
    private DynamicDataSourceManager dataSourceManager;
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 应用启动完成后初始化数据源
        try {
            dataSourceManager.initializeDataSources();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}