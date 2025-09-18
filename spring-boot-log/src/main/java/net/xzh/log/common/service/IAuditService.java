package net.xzh.log.common.service;

import net.xzh.log.common.model.Audit;

/**
 * 审计日志接口
 *
 */
public interface IAuditService {
    void save(Audit audit);
}
