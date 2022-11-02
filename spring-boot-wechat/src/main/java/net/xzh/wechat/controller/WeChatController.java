package net.xzh.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import net.xzh.wechat.util.WeixinUtil;

/**
 * 微信
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {
	@Value("${wechat.wx_appid}")
	private String wx_appid;
	@Value("${wechat.wx_pay_key}")
	private String wx_pay_key;

	/**
	 * 跳转到微信首页
	 * 
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@GetMapping(value = "/index")
	public ModelAndView login(HttpSession session, ModelAndView modelAndView) {
		modelAndView.setViewName("index");
		return modelAndView;
	}

	/**
	 * 登出
	 * 
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@PostMapping(value = "/logout")
	@ResponseBody
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "forward:/wechat/index";
	}

	/**
	 * 静默授权
	 * 
	 * @param returnUrl
	 * @return
	 */
	@GetMapping(value = "/authorize")
	public void authorize(@RequestParam("returnUrl") String returnUrl, HttpServletResponse response) {
		String redirectUrl = WeixinUtil.oauth2buildAuthorization_base(wx_appid, returnUrl);
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
	@GetMapping(value = "/userInfo")
	@ResponseBody
	public ModelAndView userInfo(@RequestParam("code") String code, @RequestParam("state") String state,
			ModelAndView modelAndView, HttpSession session) {
		try {
			JSONObject accessToken = WeixinUtil.oauth2getAccessToken(wx_appid, wx_pay_key, code);
			String openid = StrUtil.toString(accessToken.get("openid"));
			// 当前open是否绑定用户，没有去绑定页面，已绑定直接创建会话
			session.setAttribute("user", openid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}
}
