package net.xzh.redis.service;

/**
 * 会员管理Service
 */
public interface UmsMemberService {

    /**
     * 生成验证码
     */
    String generatePhoneCode(String telephone);

    /**
     * 获取验证码
     */
    String extractPhoneCode(String telephone);

}
