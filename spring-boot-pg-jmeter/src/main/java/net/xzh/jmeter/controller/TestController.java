package net.xzh.jmeter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询性能调优
 * 
 * @author xzh
 *
 */
@RestController
public class TestController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/")
	public List<Map<String, Object>> list(Long id) {
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList("select * from test where id = ?",id);
		return queryForList;
	}

}