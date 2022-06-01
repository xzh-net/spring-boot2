package net.xzh.log.common.service.impl;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.xzh.log.common.domain.WebLog;
import net.xzh.log.common.service.IWebLogService;

/**
 * 操作日志实现类-打印日志
 *
 * @author xzh
 * @date 2020/2/3
 */
@Slf4j
@Service
public class LoggerWebServiceImpl implements IWebLogService {
	private static final String MSG_PATTERN = "{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}|{}";

	/**
	 * 格式为：{时间}|{应用名}|{类名}|{方法名}|{请求方式}|{ip}|{操作信息}|{请求参数}|{返回结果}|{总耗时}|{用户id}|{用户名}
	 * 例子：2022-02-04
	 */
	@Override
	public void save(WebLog weblog) {
		log.debug(MSG_PATTERN, weblog.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")),
				weblog.getApplicationName(), weblog.getClassName(), weblog.getMethodName(), weblog.getMethod(),
				weblog.getIp(), weblog.getOperation(), weblog.getParameter(), weblog.getResult(), weblog.getTotalTime(),
				weblog.getUserId(), weblog.getUserName());
	}
}
