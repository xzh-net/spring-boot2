package net.xzh.log.common.service;

import net.xzh.log.common.domain.WebLog;

/**
 * 操作日志接口
 *
 * @author xzh
 * @date 2022/2/3
 */
public interface IWebLogService {
	void save(WebLog weblog);
}
