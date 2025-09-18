package net.xzh.quartz.schedule;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * cron表达式执行器
 * update 20250918
 */
@Component
public class CronExpJob extends QuartzJobBean {
	
	private static final Logger log = LoggerFactory.getLogger(CronExpJob.class);
	
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String message = jobDataMap.getString("message");
        log.info("cron表达式执行器：{}",message);
    }
}
