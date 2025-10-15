package net.xzh.log.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.xzh.log.model.Audit;
import net.xzh.log.service.IAuditService;

/**
 * 审计日志实现类-打印日志
 *
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "audit-log.log-type", havingValue = "logger", matchIfMissing = true)
public class LoggerAuditServiceImpl implements IAuditService {
	private static final String MSG_PATTERN = "{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}";

	@Override
	@Async
	public void save(Audit audit) {
		log.info(MSG_PATTERN, audit.getId() != null ? audit.getId() : "",
				audit.getTitle() != null ? audit.getTitle() : "",
				audit.getBusinessType() != null ? audit.getBusinessType() : "",
				audit.getMethod() != null ? audit.getMethod() : "",
				audit.getRequestMethod() != null ? audit.getRequestMethod() : "",
				audit.getOperatorType() != null ? audit.getOperatorType() : "",
				audit.getOperUrl() != null ? audit.getOperUrl() : "",
				audit.getOperIp() != null ? audit.getOperIp() : "",
				audit.getOperLocation() != null ? audit.getOperLocation() : "",
				audit.getOperParam() != null ? audit.getOperParam() : "",
				audit.getJsonResult() != null ? audit.getJsonResult() : "",
				audit.getStatus() != null ? audit.getStatus() : "",
				audit.getErrorMsg() != null ? audit.getErrorMsg() : "",
				audit.getCostTime() != null ? audit.getCostTime() : "",
				audit.getCreateUserId() != null ? audit.getCreateUserId() : "",
				audit.getCreateTime() != null ? audit.getCreateTime() : "",
				audit.getDeptId() != null ? audit.getDeptId() : "",
				audit.getDeptName() != null ? audit.getDeptName() : "",
				audit.getOperUsername() != null ? audit.getOperUsername() : "");
	}
}
