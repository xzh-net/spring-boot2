package net.xzh.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import net.xzh.wechat.util.WeChatUtil;

/**
 * 微信授权
 */
@RestController
@RequestMapping("/wechat")
public class WeChatAuthorizeController {
	@Value("${wechat.appid}")
	private String wx_app_id;
	@Value("${wechat.appsecret}")
	private String wx_app_secret;

	/**
	 * 静默授权
	 * 
	 * @param returnUrl
	 * @return
	 */
	@GetMapping(value = "/authorize")
	public void authorize(@RequestParam("returnUrl") String returnUrl, HttpServletResponse response) {
		String redirectUrl = WeChatUtil.oauth2buildAuthorization_base(wx_app_id, returnUrl);
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 静默方式授权回调地址 根据code获取token和openid
	 */
	@GetMapping(value = "/dashboard")
	@ResponseBody
	public ModelAndView dashboard(@RequestParam("code") String code, @RequestParam("state") String state,
			ModelAndView modelAndView, HttpSession session) {
		try {
			JSONObject accessToken = WeChatUtil.oauth2getAccessToken(wx_app_id, wx_app_secret, code);
			String openid = StrUtil.toString(accessToken.get("openid"));
			// 当前open是否绑定用户，没有去绑定页面，已绑定直接创建会话
			session.setAttribute("openid", openid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}
}
