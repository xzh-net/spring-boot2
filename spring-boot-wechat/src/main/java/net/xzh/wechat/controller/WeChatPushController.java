package net.xzh.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.wechat.util.WeChatUtil;

/**
 * 微信公众号消息推送
 */
@RestController
@RequestMapping("/wechat")
public class WeChatPushController {
	@Value("${wechat.appid}")
	private String wx_app_id;
	@Value("${wechat.appsecret}")
	private String wx_app_secret;
	@Value("${wechat.templateid}")
	private String templateid;
	
	/**
	 * 发送文本消息
	 */
	@PostMapping(value = "/pushMessage")
	@ResponseBody
	public String pushMessage(@RequestParam("openid") String openid) {
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("content", "您有一条新的待办未处理");
		WeChatUtil.sendMessage(wx_app_id,wx_app_secret,openid,msg);
		return "1";
	}
	
	/**
	 * 发送模板消息
	 */
	@PostMapping(value = "/pushTemplate")
	@ResponseBody
	public String pushTemplate(@RequestParam("openid") String openid) {
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("first", "您好，您有一条新的待办审批");
		msg.put("keyword1", "办公管理平台");// 业务系统
		msg.put("keyword2", "物资管理");// 所属业务
		msg.put("keyword3", "关于中秋节放假期间办公室物资整理");// 代办事项
		msg.put("keyword4", "张三");// 申请人
		msg.put("keyword5", "待审批");// 当前环节
		msg.put("remark", "请即时处理");// 备注信息
		msg.put("url", "https://www.baidu.com/s?wd=java");//打开连接
		WeChatUtil.sendTemplate(wx_app_id,wx_app_secret,templateid,openid,msg);
		return "1";
	}
}
