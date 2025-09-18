package net.xzh.quartz.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.quartz.common.model.CommonResult;
import net.xzh.quartz.schedule.cronExpJob;
import net.xzh.quartz.schedule.fixSecondJob;
import net.xzh.quartz.schedule.fixTimeJob;
import net.xzh.quartz.service.ScheduleService;

/**
 * 定时任务调度相关接口 update 20250918
 */
@Api(tags = "定时任务")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@ApiOperation("指定时间执行")
	@PostMapping("/scheduleFixTimeJob")
	public CommonResult<?> scheduleFixTimeJob(
			@RequestParam(required = true, defaultValue = "2025-09-18 16:00:00") String startTime,
			@RequestParam String message) {
		Date date = DateUtil.parse(startTime, DatePattern.NORM_DATETIME_FORMAT);
		String jobName = scheduleService.scheduleFixTimeJob(fixTimeJob.class, date, message);
		return CommonResult.success(jobName);
	}

	@ApiOperation("过?秒执行")
	@PostMapping("/scheduleFixSecondJob")
	public CommonResult<?> scheduleFixSecondJob(@RequestParam Integer second, @RequestParam String message) {
		String jobName = scheduleService.scheduleFixSecondJob(fixSecondJob.class, second, message);
		return CommonResult.success(jobName);
	}

	@ApiOperation("按照表达式执行")
	@PostMapping("/scheduleCronExpJob")
	public CommonResult<?> cronExpJob(@RequestParam(required = true, defaultValue = "*/10 * * * * ?") String cron,
			@RequestParam String message) {
		String jobName = scheduleService.scheduleCronExpJob(cronExpJob.class, cron, message);
		return CommonResult.success(jobName);
	}

	@ApiOperation("取消任务")
	@PostMapping("/cancelJob")
	public CommonResult<?> cancelJob(@RequestParam String jobName) {
		Boolean success = scheduleService.cancelJob(jobName);
		return CommonResult.success(success);
	}
}
