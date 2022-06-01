# Dubbo 2.7.7

访问地址：http://127.0.0.1:8090/doc.html 


1. 使用zk为注册中心
2. 不使用zk可以配置url进行点对点调 @DubboReference(url = "${dubbo.testurl}")

```bash
mvn clean compile
mvn clean package
```