package net.xzh.redis.service;

/**
 * 会员管理Service
 * Created 2018/8/3.
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     */
    String generateAuthCode(String telephone);

    /**
     * 判断验证码和手机号码是否匹配
     */
    boolean verifyAuthCode(String telephone, String authCode);

}
