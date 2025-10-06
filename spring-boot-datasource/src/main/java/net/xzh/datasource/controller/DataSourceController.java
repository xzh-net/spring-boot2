package net.xzh.datasource.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import net.xzh.datasource.component.DataSourceManageService;
import net.xzh.datasource.model.DynamicDataSourceConfig;

/**
 * 数据源管理
 * @author CR7
 *
 */
@RestController
@RequestMapping("/api/datasource")
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dynamic")
public class DataSourceController {

	@Autowired
	private DataSourceManageService dataSourceManageService;

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody DataSourceRequest request) {
		try {
			boolean result = dataSourceManageService.addDataSource(
                    request.getDataSourceKey(),
                    request.getDataSourceName(),
                    request.getHost(),
                    request.getPort(),
                    request.getDataBaseName(),
                    request.getUsername(),
                    request.getPassword());
			Map<String, Object> response = new HashMap<>();
			response.put("success", result);
			response.put("message", "添加成功");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.ok(response);
		}
	}

	@DeleteMapping("/remove/{datasourceKey}")
	public ResponseEntity<?> remove(@PathVariable String datasourceKey) {
		try {
			boolean result = dataSourceManageService.removeDataSource(datasourceKey);
			Map<String, Object> response = new HashMap<>();
			response.put("success", result);
			response.put("message", "删除成功");
			return ResponseEntity.ok(response);
			
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/list")	
    public ResponseEntity<?> list() {
        Set<String> dataSources = dataSourceManageService.getAllDataSources();
        List<DynamicDataSourceConfig> configs = dataSourceManageService.getAllDataSourceConfigs();
        Map<String, Object> response = new HashMap<>();
		response.put("dataSources", dataSources);
		response.put("configs", configs);
		return ResponseEntity.ok(response);
    }
	
	@PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody DataSourceTestRequest request) {
        try {
            boolean result = dataSourceManageService.testDataSource(
                    request.getHost(),
                    request.getPort(),
                    request.getDataBaseName(),
                    request.getUsername(),
                    request.getPassword());
            Map<String, Object> response = new HashMap<>();
    		response.put("success", result);
    		response.put("message", "测试连接成功");
    		return ResponseEntity.ok(response);
        } catch (Exception e) {
        	Map<String, Object> response = new HashMap<>();
    		response.put("success", false);
    		response.put("message", e.getMessage());
    		return ResponseEntity.ok(response);
        }
    }

	@Data
    public static class DataSourceRequest {
        private String dataSourceKey;
        private String dataSourceName;
        private String host;
        private String port;
        private String dataBaseName;
        private String username;
        private String password;
    }
    
    @Data
    public static class DataSourceTestRequest {
        private String host;
        private String port;
        private String dataBaseName;
        private String username;
        private String password;
    }
}