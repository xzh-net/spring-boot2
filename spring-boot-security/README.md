# spring-boot-security

访问地址：http://127.0.0.1:8080/doc.html

```bash
mvn clean compile
mvn clean package
```

## 动态权限

认证步骤：登录授权过滤器->动态权限过滤器，处理白名单->动态权限决策管理器

1. 实现FilterInvocationSecurityMetadataSource类，获取动态权限数据源
    - ```
      @PostConstruct
      public void loadDataSource() {
      	configAttributeMap = dynamicSecurityService.loadDataSource();
      }
      ```
      
    - 重写的support方法都返回true
    
2. 实现AccessDecisionManager类，权限决策器

      - 重写的support方法都返回true

3. SecurityConfig配置类中完成相应配置

      - ```
        //有动态权限配置时添加动态权限校验过滤器，权限过滤器会调用决策器AccessDecisionManager中的decide方法
        if(dynamicSecurityService!=null){
        	registry.and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
        }
        ```

