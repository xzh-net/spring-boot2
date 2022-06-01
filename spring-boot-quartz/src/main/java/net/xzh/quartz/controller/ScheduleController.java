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
import net.xzh.quartz.job.CronProcessJob;
import net.xzh.quartz.job.SendEmailJob;
import net.xzh.quartz.job.SendMessageJob;
import net.xzh.quartz.service.ScheduleService;

/**
 * 定时任务调度相关接口
 * Created by macro on 2020/9/29.
 */
@Api(tags = "定时任务调度")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    
    @ApiOperation("指定时间发送邮件")
    @PostMapping("/sendEmail")
    public CommonResult sendEmail(@RequestParam String startTime,@RequestParam String data) {
        Date date = DateUtil.parse(startTime, DatePattern.NORM_DATETIME_FORMAT);
        String jobName = scheduleService.scheduleFixTimeJob(SendEmailJob.class, date, data);
        return CommonResult.success(jobName);
    }

    @ApiOperation("过?秒发送站内信")
    @PostMapping("/sendMessage")
    public CommonResult sendMessage(@RequestParam Integer second,@RequestParam String data) {
        String jobName = scheduleService.scheduleFixSecondJob(SendMessageJob.class, second, data);
        return CommonResult.success(jobName);
    }

    @ApiOperation("通过CRON表达式调度任务")
    @PostMapping("/scheduleJob")
    public CommonResult scheduleJob(@RequestParam String cron, @RequestParam String data) {
        String jobName = scheduleService.scheduleJob(CronProcessJob.class, cron, data);
        return CommonResult.success(jobName);
    }

    @ApiOperation("取消定时任务")
    @PostMapping("/cancelScheduleJob")
    public CommonResult cancelScheduleJob(@RequestParam String jobName) {
        Boolean success = scheduleService.cancelScheduleJob(jobName);
        return CommonResult.success(success);
    }
}
