package net.xzh.jasypt.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * SM4 加密工具类
 */
public class SM4Util {
    
    static {
        // 添加 Bouncy Castle 提供者
        Security.addProvider(new BouncyCastleProvider());
    }
    
    private static final String ALGORITHM_NAME = "SM4";
    private static final String TRANSFORMATION = "SM4/ECB/PKCS5Padding";
    
    /**
     * 生成 SM4 密钥
     */
    public static byte[] generateKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, "BC");
        kg.init(128);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }
    
    /**
     * SM4 加密
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM_NAME);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }
    
    /**
     * SM4 解密
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM_NAME);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION, "BC");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encryptedData);
    }
    
    /**
     * 加密字符串
     */
    public static String encrypt(String data, String hexKey) throws Exception {
        byte[] key = Hex.decode(hexKey);
        byte[] encryptedData = encrypt(data.getBytes("UTF-8"), key);
        return Hex.toHexString(encryptedData);
    }
    
    /**
     * 解密字符串
     */
    public static String decrypt(String encryptedData, String hexKey) throws Exception {
        byte[] key = Hex.decode(hexKey);
        byte[] data = decrypt(Hex.decode(encryptedData), key);
        return new String(data, "UTF-8");
    }
    
    /**
     * 生成十六进制格式的密钥
     */
    public static String generateHexKey() throws Exception {
        byte[] key = generateKey();
        return Hex.toHexString(key);
    }
    
}