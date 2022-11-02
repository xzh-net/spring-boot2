package net.xzh.wechat.util;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;

/**
 * 微信公众平台通用接口工具类
 * 
 * @author xzh
 */
public class WeixinUtil {

	// 静默授权地址
	private static String auth_snsapi_base = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_uri}&response_type=code&scope=snsapi_base&state={state}#wechat_redirect";
	// 非静默授权地址
	private static String auth_snsapi_userinfo = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_uri}&response_type=code&scope=snsapi_userinfo&state={state}#wechat_redirect";
	// 获取token
	private static String auth_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";

	/**
	 * 生成静默授权地址
	 * 
	 * @param wx_appid
	 * @param auth_snsapi_base
	 * @param encode
	 * @return
	 */
	public static String oauth2buildAuthorization_base(String wx_appid, String returnUrl) {
		return auth_snsapi_base.replace("{appid}", wx_appid).replace("{redirect_uri}", returnUrl).replace("{state}",
				System.currentTimeMillis() + "");
	}

	/**
	 * 生成非静默授权地址
	 * 
	 * @param wx_appid
	 * @param encode
	 * @return
	 */
	public static String oauth2buildAuthorization_userinfo(String wx_appid, String returnUrl) {
		return auth_snsapi_userinfo.replace("{appid}", wx_appid).replace("{redirect_uri}", returnUrl).replace("{state}",
				System.currentTimeMillis() + "");
	}

	/**
	 * 获取token
	 * 
	 * @param code
	 * @param code2
	 * @throws Exception
	 */
	public static JSONObject oauth2getAccessToken(String wx_appid, String wx_pay_key, String code) throws Exception {
		String url = auth_access_token.replace("{appid}", wx_appid).replace("{secret}", wx_pay_key).replace("{code}",
				code);
		String json = HttpUtil.get(url);
		JSONObject jsonObject = JSONObject.parseObject(json);
		if (!jsonObject.containsKey("errcode")) {
			return jsonObject;
		}
		throw new Exception("getAccessToken error : " + jsonObject);

	}

}