package net.xzh.datasource.service;

import java.util.List;

import net.xzh.datasource.model.DynamicDataSourceConfig;

public interface DynamicDataSourceConfigService {

	public List<DynamicDataSourceConfig> getActiveConfigs();
	
	public DynamicDataSourceConfig getByKey(String datasourceKey);
	
	public boolean save(DynamicDataSourceConfig config);
	
	public boolean remove(String datasourceKey);
	
	public boolean exists(String datasourceKey);
}
