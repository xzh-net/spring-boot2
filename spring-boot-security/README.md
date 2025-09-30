# Spring Boot整合JWT和Spring Security进行权限校验

## 方式一：注解和配置类

1. 开启方法级权限控制

   ```java
   @EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
   @Configuration
   public class SecurityConfig {
   }
   ```

   

2. 添加注解

   ```java
   @PreAuthorize("@sss.hasPermi('6:admin:resetPwd')")
   @ApiOperation(value = "重置密码")
   @RequestMapping(value = "/resetPwd/{id}", method = RequestMethod.PUT)
   public CommonResult<?> resetPwd(@PathVariable Long id) {
       return CommonResult.success("重置成功");
   }
   ```

   

3. 创建工具类

   ```java
   /**
    * 自定义权限实现
    * 
    */
   @Service("sss")
   public class SpringSecurityService {
   ```

   




## 方式二：动态权限校验

动态权限扩展接口（DynamicSecurityService），本身不是Security的标准组件，而是一个常用于自定义、动态权限管理方案中的核心接口，通过调用 loadDataSource 等方法在运行时刷新规则，从而实现无需重启应用即可生效的权限管理

1. 开启DynamicSecurityService，增加过滤器
2. DynamicSecurityFilter 拦截过滤器
3. DynamicSecurityMetadataSource 提供权限规则
4. DynamicAccessDecisionManager 执行权限判断



## 认证机制

使用 Spring Security 的 AuthenticationManager 进行认证，委托给 UserDetailsService 执行实际的认证逻辑，标准化流程。

> 其他方式：手动获取 UserDetails 并检查密码，直接操作 SecurityContextHolder，绕过 Spring Security 的认证管理器。
> 参考项目：spring-boot-mybatis-plus




## 总结对比

| 维度         | @PreAuthorize       | DynamicSecurityService |
| :----------- | :------------------ | :--------------------- |
| **控制层级** | 方法级别            | URL/请求级别           |
| **动态性**   | 较低（编码时确定）  | 高（运行时可调整）     |
| **维护性**   | 分散在代码中        | 集中管理               |
| **灵活性**   | 高（支持复杂SpEL）  | 中等（URL-权限映射）   |
| **性能**     | 按需检查，有AOP开销 | 每次请求检查，需缓存   |
| **适用场景** | 复杂业务逻辑权限    | REST API权限管理       |

**推荐策略**：大多数项目建议结合使用：

- 用 `DynamicSecurityService` 管理接口访问权限
- 用 `@PreAuthorize` 处理复杂的业务逻辑权限



## 其他

JWT、JWS、JWE三者之间的关系，其实JWT(JSON Web Token)指的是一种规范，这种规范允许我们使用JWT在两个组织之间传递安全可靠的信息。而JWS(JSON Web Signature)和JWE(JSON Web Encryption)是JWT规范的两种不同实现，我们平时最常使用的实现就是JWS。

jwt对称非对称使用方式：

- 对称加密（HMAC）,使用相同的秘钥来进行加密和解密
- 非对称加密（RSA）,用公钥和私钥来进行加密解密。对于加密操作，公钥负责加密，私钥负责解密。对于签名操作，私钥负责签名，公钥负责验证

```bash
 keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
 # 输入密码为123456，然后输入各种信息之后就可以生成证书jwt.jks
```



## 整合SpringDoc 1.7.0

http://127.0.0.1:8080/swagger-ui/index.html



