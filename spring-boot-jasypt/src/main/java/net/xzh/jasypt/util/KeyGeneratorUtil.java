package net.xzh.jasypt.util;

/**
 * 密钥生成工具
 */
public class KeyGeneratorUtil {

	public static void main(String[] args) {
		try {
			// 生成 SM4 密钥
			String hexKey = SM4Util.generateHexKey();
			System.out.println("Generated SM4 Key: " + hexKey);
			System.out.println("Key length: " + hexKey.length());

			// 测试加密解密
			String originalText = "root";
			String encrypted = SM4Util.encrypt(originalText, hexKey);
			String decrypted = SM4Util.decrypt(encrypted, hexKey);

			System.out.println("Original: " + originalText);
			System.out.println("Encrypted: " + encrypted);
			System.out.println("Decrypted: " + decrypted);
			System.out.println("Match: " + originalText.equals(decrypted));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}