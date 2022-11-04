# Spring Boot 2.3.0.RELEASE

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.0.RELEASE</version>
    <relativePath />
</parent>
```



## 1. 基础整合

```lua
spring-boot-stand       --基础项目
spring-boot-swagger     --整合swagger
spring-boot-knife4j     --整合knife4j
spring-boot-webflux     --异步非阻塞式Web框架
spring-boot-hikaricp    --数据源
spring-boot-email       --发送邮件
spring-boot-quartz      --定时任务和线程池
spring-boot-elk         --日志收集
spring-boot-etl         --数据转换
spring-boot-elasticsearch   --商品搜索
spring-boot-mongo       --浏览记录读写分离
spring-boot-hbase       --hbase
spring-boot-hdfs        --hadoop
spring-boot-redis       --常用操作整合
spring-boot-activiti    --工作流设计器，groups验证分组，druid监控
spring-boot-jenkins     --持续集成
spring-boot-jwt         --登录认证
spring-boot-security    --动态授权
spring-boot-wechat      --微信网页授权
spring-boot-sharding-jdbc	--分库分表
```

## 2. 自定义starter

```lua
spring-boot-log	--日志拦截器
spring-boot-oss	--附件服务器
```

## 3. 工具类

```lua
spring-boot-generator   --代码生成工具
spring-boot-validation  --逻辑校验的三种方式
```

## 4. 消息中间件

```lua
spring-boot-activemq
spring-boot-rabbitmq
spring-boot-rocketmq
spring-boot-kafka
spring-boot-pulsar
```

## 5. 聊天

```lua
spring-boot-netty
```

## 6. RPC

```lua
spring-boot-dubbo
spring-boot-cxf
```


# 目录结构
> 调试

```lua
root
├── common (模块级)
│   ├── annotation
│   ├── aspect
│   ├── component
│   ├── config
│   ├── constant
│   ├── context
│   ├── exception
│   ├── extension
│   ├── feign
│   ├── model
│   ├── auth
│   ├── token
│   ├── properties
│   ├── lock
│   ├── resolver
│   ├── service
│   └── utils
├── controller (项目级)
│   ├── BaseAction.java
│   ├── SysUserController.java
│   ├── UserAccountController.java
│   ├── SysMenuController.java
│   └── SysRoleController.java
├── config (项目级)
│   ├── MyBatisConfig.java
│   ├── Swagger2Config.java
│   └── RedisExpireConfig.java
├── properties
├── dto
├── model
├── dao
├── mapper
├── service
└── utils
```