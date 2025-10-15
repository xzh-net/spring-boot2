package net.xzh.log.service.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariDataSource;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import net.xzh.log.model.Audit;
import net.xzh.log.properties.LogDbProperties;
import net.xzh.log.service.IAuditService;

/**
 * 审计日志实现类-数据库
 *
 */
@Service
@ConditionalOnProperty(name = "audit-log.log-type", havingValue = "db")
@ConditionalOnClass(JdbcTemplate.class)
public class DbAuditServiceImpl implements IAuditService {

	private static final String INSERT_SQL = "INSERT INTO ts_oper_log "
			+ "(id, title, business_type, method, request_method, operator_type, "
			+ "oper_url, oper_ip, oper_location, oper_param, json_result, "
			+ "status, error_msg, cost_time, create_user_id, create_time, " + "dept_id, dept_name, oper_username) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final JdbcTemplate jdbcTemplate;

	public DbAuditServiceImpl(@Autowired(required = false) LogDbProperties logDbProperties, DataSource dataSource) {
		// 优先使用配置的日志数据源，否则使用默认的数据源
		if (logDbProperties != null && StrUtil.isNotEmpty(logDbProperties.getJdbcUrl())) {
			dataSource = new HikariDataSource(logDbProperties);
		}
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Async
	@Override
	public void save(Audit audit) {
		long id = IdUtil.getSnowflakeNextId();
		this.jdbcTemplate.update(INSERT_SQL, id, // id
				audit.getTitle(), audit.getBusinessType(), audit.getMethod(), audit.getRequestMethod(),
				audit.getOperatorType(), audit.getOperUrl(), audit.getOperIp(), audit.getOperLocation(),
				audit.getOperParam(), audit.getJsonResult(), audit.getStatus(), audit.getErrorMsg(),
				audit.getCostTime(), audit.getCreateUserId(), audit.getCreateTime(), audit.getDeptId(),
				audit.getDeptName(), audit.getOperUsername());
	}
}
