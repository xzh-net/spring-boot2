# Spring Boot整合ActiveMQ

JMS (Java Message Service) JAVA消息服务，基于JVM消息的规范。ActiveMQ、HornetMQ是JMS实现。

JMS提供了两种消息模型：点对点、发布订阅。支持五种消息类型：TextMessage 、MapMessage 、BytesMessage 、StreamMessage 、ObjectMessage。

spring-jms提供了JMS的支持，JmsMessagingTemplate对JmsTemplate进行了封装，使用@JmsListener监听消息。

访问地址：http://127.0.0.1:8080/doc.html 


```bash
mvn clean compile
mvn clean package
```

