package net.xzh.log.service;

import net.xzh.log.model.Audit;

/**
 * 审计日志接口
 *
 */
public interface IAuditService {
    void save(Audit audit);
}
