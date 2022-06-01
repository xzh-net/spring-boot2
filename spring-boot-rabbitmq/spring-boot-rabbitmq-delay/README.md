# Delay

访问地址：http://127.0.0.1:8080/doc.html

1. 死信队列DLX
2. 延迟队列，可实现不同间隔的延迟彼此不受影响，基于插件rabbitmq-delayed-message-exchange
3. 发送确认
4. 消费手动确认
5. 消费限流
6. 消息追踪

```bash
mvn clean compile
mvn clean package
```