package net.xzh.quartz.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import net.xzh.quartz.schedule.CronExpJob;
import net.xzh.quartz.schedule.FixSecondJob;
import net.xzh.quartz.schedule.FixTimeJob;
import net.xzh.quartz.service.ScheduleService;

/**
 * 定时任务调度相关接口 update 20250918
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 指定时间执行
	 * @param startTime
	 * @param message
	 * @return
	 */
	@PostMapping("/scheduleFixTimeJob")
	public Object scheduleFixTimeJob(
			@RequestParam(required = true, defaultValue = "2025-09-18 16:00:00") String startTime,
			@RequestParam String message) {
		Date date = DateUtil.parse(startTime, DatePattern.NORM_DATETIME_FORMAT);
		return scheduleService.scheduleFixTimeJob(FixTimeJob.class, date, message);
	}

	/**
	 * 过?秒执行
	 * @param second
	 * @param message
	 * @return
	 */
	@PostMapping("/scheduleFixSecondJob")
	public Object scheduleFixSecondJob(@RequestParam Integer second, @RequestParam String message) {
		return scheduleService.scheduleFixSecondJob(FixSecondJob.class, second, message);
	}

	/**
	 * 按照表达式执行
	 * @param cron
	 * @param message
	 * @return
	 */
	@PostMapping("/scheduleCronExpJob")
	public Object cronExpJob(@RequestParam(required = true, defaultValue = "*/10 * * * * ?") String cron,
			@RequestParam String message) {
		return scheduleService.scheduleCronExpJob(CronExpJob.class, cron, message);
	}

	/**
	 * 取消任务
	 * @param jobName
	 * @return
	 */
	@PostMapping("/cancelJob")
	public Object cancelJob(@RequestParam String jobName) {
		return scheduleService.cancelJob(jobName);
	}
}
