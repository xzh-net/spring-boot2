package net.xzh.quartz.service;

import org.quartz.Job;

import java.util.Date;

/**
 * Quartz定时任务操作类
 * Created 2020/9/27.
 */
public interface ScheduleService {
    /**
     * 通过CRON表达式调度任务
     */
    String scheduleCronExpJob(Class<? extends Job> jobBeanClass, String cron, String message);

    /**
     * 调度指定时间的任务
     */
    String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String message);
    
    /**
     *
     */
    String scheduleFixSecondJob(Class<? extends Job> jobBeanClass, int second, String message);

    /**
     * 取消定时任务
     */
    Boolean cancelJob(String jobName);
}
