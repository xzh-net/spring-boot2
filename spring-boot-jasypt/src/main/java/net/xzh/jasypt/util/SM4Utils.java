package net.xzh.jasypt.util;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class SM4Utils {

	//key必须是16字节，即128位
	final static String key = "GYLywLS3ZMQvM0xt";

	//指明加密算法和秘钥
	static SymmetricCrypto sm4 = new SymmetricCrypto("SM4/ECB/PKCS5Padding", key.getBytes());

	/**
	 * 加密为16进制，也可以加密成base64/字节数组
	 *
	 * @param plaintext
	 * @return
	 */
	public String encryptSm4(String plaintext) {
		if (StrUtil.isBlank(plaintext)) {
			return "";
		}
		return sm4.encryptHex(plaintext);
	}

	/**
	 * 解密
	 *
	 * @param ciphertext
	 * @return
	 */
	public String decryptSm4(String ciphertext) {
		if (StrUtil.isBlank(ciphertext)) {
			return "";
		}
		return sm4.decryptStr(ciphertext);
	}
	
	public static void main(String[] args) {
//		SM4Utils sm4=new SM4Utils();
//		String text="root";
//	    String encryptHex=sm4.encryptSm4(text);
//		System.out.println("加密后：" + encryptHex);
//		System.out.println("解密后:"+ sm4.decryptSm4(encryptHex));
		
	    String text = "root";
        SymmetricCrypto sm4 = SmUtil.sm4("GYLywLS3ZMQvM0xt".getBytes());
        String encryptHex = sm4.encryptHex(text);
        System.out.println("加密后：" + encryptHex);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密后：" + decryptStr);
	}
}

