package net.xzh.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 使用CRON表达式的任务执行器
 * Created by macro on 2020/9/29.
 */
@Component
public class CronProcessJob extends QuartzJobBean {
	
	private static final Logger log = LoggerFactory.getLogger(CronProcessJob.class);
	
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String data = jobDataMap.getString("data");
        log.info("CRON表达式任务执行：{}",data);
    }
}
