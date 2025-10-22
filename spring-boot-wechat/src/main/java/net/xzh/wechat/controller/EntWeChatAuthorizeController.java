package net.xzh.wechat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 企业微信授权
 */
@RestController
@RequestMapping("/entwechat")
public class EntWeChatAuthorizeController {
	
	@Value("${wechat2.corpid}")
	private String corpid;
	@Value("${wechat2.corpsecret}")
	private String corpsecret;
	
	private static String auth_access_token = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
	private static String auth_access_userinfo = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=";
	private static String auth_access_openid = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid";
	
	@RequestMapping(value = "/authorize")
	@ResponseBody
	public ModelAndView login(HttpSession session, ModelAndView modelAndView,@RequestParam String code) {
		// 获取应用access_token
		String body = HttpUtil.get(auth_access_token + "?corpid=" + corpid + "&corpsecret=" + corpsecret);
		JSONObject jsonObject = JSONUtil.parseObj(body);
		String access_token = (String) jsonObject.get("access_token");
		// 获取个人基本信息
		body= HttpUtil.get(auth_access_userinfo + access_token + "&code=" + code);
		// userid转openid
		jsonObject = JSONUtil.parseObj(body);
		String userid = (String) jsonObject.get("UserId");
        JSONObject user = new JSONObject();
        user.put("userid", userid);
        body = HttpUtil.post(auth_access_openid + "?access_token=" + access_token , user.toString());
		session.setAttribute("body", body);
		modelAndView.setViewName("dashboard2");
		return modelAndView;
	}
}
