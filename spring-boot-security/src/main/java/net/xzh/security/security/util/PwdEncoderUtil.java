package net.xzh.security.security.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import net.xzh.security.security.passwordEncoder.SM3PasswordEncoder;

/**
 * PasswordEncoder实现工具类
 *
 * add by xzh 2024/09/29
 */
public class PwdEncoderUtil {
    public static PasswordEncoder getDelegatingPasswordEncoder(String encodingId) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("SM3", new SM3PasswordEncoder());
        Assert.isTrue(encoders.containsKey(encodingId), encodingId + " is not found in idToPasswordEncoder");
        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(encoders.get(encodingId));
        return delegatingPasswordEncoder;
    }
}