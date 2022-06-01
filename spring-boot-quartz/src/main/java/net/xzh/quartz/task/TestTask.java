package net.xzh.quartz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.hutool.core.date.DateTime;

@Component
public class TestTask {

	private static final Logger log = LoggerFactory.getLogger(TestTask.class);
	
	/**
	 * cron表达式
	 */
	@Scheduled(cron = "*/10 * * * * ?")
	public void task1() {
		log.info("cron表达式每10秒一次，正在执行，现在时间：{}", DateTime.now());
	}

	/**
	 * 以固定周期执行，每10s
	 */
	@Scheduled(fixedRate = 10000)
	public void task2() {
		log.info("固定周期每10s一次，现在时间：{}", DateTime.now());
	}

	/**
	 * 上次结束时间到下一次开始时间间隔10s
	 */
	@Scheduled(fixedDelay = 10000)
	public void task3() {
		log.info("上次结束时间到下一次开始时间间隔10s，现在时间：{}", DateTime.now());
	}

	/**
	 * 第一次延迟1秒后执行，之后按固定周期执行每10s一次
	 */
	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void task4() {
		log.info("首次延迟之后固定周期执行每10s一次，现在时间：{}", DateTime.now());
	}
}
