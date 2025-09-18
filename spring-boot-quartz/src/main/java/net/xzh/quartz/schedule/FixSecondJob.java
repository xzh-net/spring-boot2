package net.xzh.quartz.schedule;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import net.xzh.quartz.service.ScheduleService;

/**
 * 多少s以后执行的执行器
 * Created 2020/9/27.
 */
@Component
public class FixSecondJob extends QuartzJobBean {
	
	private static final Logger log = LoggerFactory.getLogger(FixSecondJob.class);
	
    @Autowired
    private ScheduleService scheduleService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String message = jobDataMap.getString("message");
        log.info("多少s以后执行的执行器：{}",message);
        //完成后删除触发器和任务
        scheduleService.cancelJob(trigger.getKey().getName());
    }
}
