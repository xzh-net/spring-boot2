# JWT

JWT、JWS、JWE三者之间的关系，其实JWT(JSON Web Token)指的是一种规范，这种规范允许我们使用JWT在两个组织之间传递安全可靠的信息。而JWS(JSON Web Signature)和JWE(JSON Web Encryption)是JWT规范的两种不同实现，我们平时最常使用的实现就是JWS

```bash
mvn clean compile
mvn clean package
```

访问地址：http://127.0.0.1:8080/doc.html

1. jwt实现注册登录授权
2. jwt对称非对称两种使用方式
   - 对称加密（HMAC）,使用相同的秘钥来进行加密和解密
   - 非对称加密（RSA）,用公钥和私钥来进行加密解密。对于加密操作，公钥负责加密，私钥负责解密。对于签名操作，私钥负责签名，公钥负责验证
    ```bash
    keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
    # 输入密码为123456，然后输入各种信息之后就可以生成证书jwt.jks
    ```

