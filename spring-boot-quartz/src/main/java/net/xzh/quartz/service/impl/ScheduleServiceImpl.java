package net.xzh.quartz.service.impl;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import net.xzh.quartz.service.ScheduleService;

/**
 * Quartz定时任务操作实现类
 * Created 2020/9/27.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
    @Autowired
    private Scheduler scheduler;
    private String defaultGroup = "default_group";

    @Override
    public String scheduleCronExpJob(Class<? extends Job> jobBeanClass, String cron, String message) {
        // 创建需要执行的任务
        String jobName = UUID.fastUUID().toString();
        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                .withIdentity(jobName, defaultGroup)
                .usingJobData("message", message)
                .build();
        //创建触发器，指定任务执行时间
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, defaultGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        //使用调度器进行任务调度
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.info("创建定时任务失败！");
        }
        return jobName;
    }

    @Override
    public String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data) {
        //日期转CRON表达式
        String startCron = String.format("%d %d %d %d %d ? %d",
                DateUtil.second(startTime),
                DateUtil.minute(startTime),
                DateUtil.hour(startTime, true),
                DateUtil.dayOfMonth(startTime),
                DateUtil.month(startTime) + 1,
                DateUtil.year(startTime));
        return scheduleCronExpJob(jobBeanClass, startCron, data);
    }
    
    @Override
	public String scheduleFixSecondJob(Class<? extends Job> jobBeanClass, int second, String data) {
    	//秒转CRON表达式
    	Date newDate = DateUtil.offset(DateUtil.date(), DateField.SECOND, second);
    	return scheduleFixTimeJob(jobBeanClass,newDate,data);
	}

    
    @Override
    public Boolean cancelJob(String jobName) {
        boolean success = false;
        try {
            // 暂停触发器
            scheduler.pauseTrigger(new TriggerKey(jobName, defaultGroup));
            // 移除触发器中的任务
            scheduler.unscheduleJob(new TriggerKey(jobName, defaultGroup));
            // 删除任务
            scheduler.deleteJob(new JobKey(jobName, defaultGroup));
            success = true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return success;
    }

}
