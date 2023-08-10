package net.xzh.wechat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpUtil;

/**
 * 微信公众平台通用接口工具类
 * 
 * @author xzh
 */
public class WeChatUtil {

	// 静默授权地址
	private static String auth_snsapi_base = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_uri}&response_type=code&scope=snsapi_base&state={state}#wechat_redirect";
	// 非静默授权地址
	private static String auth_snsapi_userinfo = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_uri}&response_type=code&scope=snsapi_userinfo&state={state}#wechat_redirect";
	// 获取token
	private static String auth_access_token = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";

	// 发送文本消息
	private static String wx_message_custom = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	// 发送模板消息
	private static String wx_message_template = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	// 获取普通access_token
	private static String app_access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	// 获取jsapi_ticket
	private static String jsapi_ticket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={access_token}&type=jsapi";

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
	public static JSONObject oauth2getAccessToken(String wx_appid, String secret, String code) throws Exception {
		String url = auth_access_token.replace("{appid}", wx_appid).replace("{secret}", secret).replace("{code}", code);
		String json = HttpUtil.get(url);
		JSONObject jsonObject = JSONObject.parseObject(json);
		if (!jsonObject.containsKey("errcode")) {
			return jsonObject;
		}
		throw new Exception("getAccessToken error : " + jsonObject);
	}

	/**
	 * 发送模板消息（客服接口-发消息）
	 * 
	 * @param wx_app_id
	 * @param wx_app_secret
	 * @param templateid
	 * @param openid
	 * @param msg
	 */

	public static void sendTemplate(String wx_app_id, String wx_app_secret, String templateid, String openid,
			Map<String, String> msg) {
		Map<String, String> access_tokenMap = getAccessToken(wx_app_id, wx_app_secret);
		JSONObject first = new JSONObject();
		first.put("value", msg.get("first"));
		first.put("color", "#000000");
		JSONObject keyword1 = new JSONObject();
		keyword1.put("value", msg.get("keyword1"));
		keyword1.put("color", "#000000");
		JSONObject keyword2 = new JSONObject();
		keyword2.put("value", msg.get("keyword2"));
		keyword2.put("color", "#000000");
		JSONObject keyword3 = new JSONObject();
		keyword3.put("value", msg.get("keyword3"));
		keyword3.put("color", "#000000");
		JSONObject keyword4 = new JSONObject();
		keyword4.put("value", msg.get("keyword4"));
		keyword4.put("color", "#000000");
		JSONObject keyword5 = new JSONObject();
		keyword5.put("value", msg.get("keyword5"));
		keyword5.put("color", "#000000");
		JSONObject remark = new JSONObject();
		remark.put("value", msg.get("remark"));
		remark.put("color", "#000000");
		JSONObject data = new JSONObject();
		data.put("first", first);
		data.put("keyword1", keyword1);
		data.put("keyword2", keyword2);
		data.put("keyword3", keyword3);
		data.put("keyword4", keyword4);
		data.put("keyword5", keyword5);
		data.put("remark", remark);
		// 模板消息格式总格式
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("template_id", templateid);
		jsonObject.put("url", msg.get("url"));
		jsonObject.put("touser", openid);
		jsonObject.put("data", data);
		HttpUtil.post(wx_message_template + access_tokenMap.get("access_token"), jsonObject.toString());

	}

	/**
	 * 发送文本消息
	 * 
	 * @param wx_app_id
	 * @param wx_app_secret
	 * @param openid
	 * @param msg
	 */

	public static void sendMessage(String wx_app_id, String wx_app_secret, String openid, Map<String, String> msg) {
		Map<String, String> access_tokenMap = getAccessToken(wx_app_id, wx_app_secret);
		JSONObject data = new JSONObject();
		data.put("content", msg.get("content"));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msgtype", "text");
		jsonObject.put("text", data);
		jsonObject.put("touser", openid);
		HttpUtil.post(wx_message_custom + access_tokenMap.get("access_token"), jsonObject.toString());
	}

	/**
	 * 获取token
	 * 
	 * @param wx_app_id
	 * @param wx_app_secret
	 * @return
	 */
	public static Map<String, String> getAccessToken(String wx_app_id, String wx_app_secret) {
		app_access_token = app_access_token + "&appid=" + wx_app_id + "&secret=" + wx_app_secret;
		String token = HttpUtil.post(app_access_token, new HashMap<String, Object>());
		Map<String, String> access_tokenMap = (Map<String, String>) JSON.parse(token);
		return access_tokenMap;
	}

	/**
	 * 获取ticket
	 * 
	 * @param accessToken
	 * @return
	 */
	public static Map<String, String> getJsApiTicket(String accessToken) {
		jsapi_ticket = jsapi_ticket.replace("{access_token}", accessToken);
		String token = HttpUtil.get(jsapi_ticket, new HashMap<String, Object>());
		Map<String, String> ticketMap = (Map<String, String>) JSON.parse(token);
		return ticketMap;
	}

	/**
	 * 生成签名
	 * @param appId
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String appId,String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", appId);
		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

}