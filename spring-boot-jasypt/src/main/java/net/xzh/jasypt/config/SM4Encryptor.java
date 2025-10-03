package net.xzh.jasypt.config;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.xzh.jasypt.util.SM4Util;

/**
 * 自定义 SM4 加密器
 * 
 */
@Component("jasyptStringEncryptor")
public class SM4Encryptor implements StringEncryptor {

	@Value("${jasypt.encryptor.sm4-key:}")
	private String sm4Key;

	@Override
	public String encrypt(String message) {
		try {
			return SM4Util.encrypt(message, sm4Key);
		} catch (Exception e) {
			throw new RuntimeException("SM4 encryption failed", e);
		}
	}

	@Override
	public String decrypt(String encryptedMessage) {
		try {
			return SM4Util.decrypt(encryptedMessage, sm4Key);
		} catch (Exception e) {
			throw new RuntimeException("SM4 decryption failed", e);
		}
	}

}