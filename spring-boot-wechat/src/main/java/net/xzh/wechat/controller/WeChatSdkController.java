package net.xzh.wechat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.xzh.wechat.util.WeChatUtil;

/**
 * 微信JS-SDK
 */
@RestController
@RequestMapping("/wechat")
public class WeChatSdkController {
	@Value("${wechat.appid}")
	private String wx_app_id;
	@Value("${wechat.appsecret}")
	private String wx_app_secret;
	
	//微信参数
    private String accessToken;
    private String jsApiTicket;
    //获取参数的时刻
    private Long getTiketTime = 0L;
    private Long getTokenTime = 0L;
    //参数的有效时间,单位是秒(s)
    private Long tokenExpireTime = 0L;
    private Long ticketExpireTime = 0L;
	
	@RequestMapping(value = "/initApi")
	public Map<String, String> initApi(@RequestParam("url") String url) {
        long now = System.currentTimeMillis();
        //判断accessToken是否已经存在或者token是否过期
        if(ObjectUtil.isNull(accessToken)||(now - getTokenTime > tokenExpireTime*1000)){
        	Map<String, String> access_tokenMap = WeChatUtil.getAccessToken(wx_app_id, wx_app_secret);
            if(access_tokenMap != null){
                accessToken = Convert.toStr(access_tokenMap.get("access_token"));
                tokenExpireTime = Convert.toLong(access_tokenMap.get("expires_in"));
                getTokenTime = System.currentTimeMillis();
            }
        }
        //判断jsApiTicket是否已经存在或者是否过期
        if(StrUtil.isBlank(jsApiTicket)||(now - getTiketTime > ticketExpireTime*1000)){
        	Map<String, String> ticketInfo = WeChatUtil.getJsApiTicket(accessToken);
            if(ticketInfo!=null){
            	jsApiTicket = Convert.toStr(ticketInfo.get("ticket"));
                ticketExpireTime = Convert.toLong(ticketInfo.get("expires_in"));
                getTiketTime = System.currentTimeMillis();
            }
        }
		return WeChatUtil.sign(wx_app_id,jsApiTicket, url);
	}
}
