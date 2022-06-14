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

```
spring-boot-stand
spring-boot-swagger
spring-boot-knife4j
spring-boot-webflux
spring-boot-hikaricp
spring-boot-ureport2
spring-boot-email
spring-boot-quartz
spring-boot-elk
spring-boot-elasticsearch
spring-boot-mongo
spring-boot-hbase
spring-boot-jwt
spring-boot-redis
spring-boot-activiti
spring-boot-jenkins
spring-boot-security
```

## 2. 自定义starter

```
spring-boot-log
spring-boot-oss
```

## 3. 工具类

```
spring-boot-generator
spring-boot-validation
```

## 4. 消息中间件

```
spring-boot-activemq
spring-boot-rabbitmq
spring-boot-rocketmq	
spring-boot-kafka
```

## 5. 聊天

```
spring-boot-netty
```

## 6. RPC

```
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