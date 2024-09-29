package net.xzh.jasypt.config;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * jasypt 自定义数据库密码加密器 - SM4加密算法
 * 
 */
@Component("sm4StringEncryptor")
public class SM4StringEncryptor implements StringEncryptor {

    @Value("${jasypt.encryptor.salt}")
    private String salt;

    @Override
    public String encrypt(String message) {
        SymmetricCrypto sm4 = SmUtil.sm4(salt.getBytes());
        String encryptHex = sm4.encryptHex(message);
        return encryptHex;
    }

    @Override
    public String decrypt(String encryptedMessage) {
        SymmetricCrypto sm41 = SmUtil.sm4(salt.getBytes());
        String decryptStr = sm41.decryptStr(encryptedMessage, CharsetUtil.CHARSET_UTF_8);
        return decryptStr;
    }

}