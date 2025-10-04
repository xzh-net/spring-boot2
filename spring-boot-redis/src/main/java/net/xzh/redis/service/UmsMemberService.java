package net.xzh.redis.service;

/**
 * 会员管理Service
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     */
    String captcha(String telephone);

    /**
     * 验证码登录
     * @param code 
     */
    void loginByCaptcha(String telephone, String code);

}
