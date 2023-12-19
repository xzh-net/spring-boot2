# SockJS 

SockJS 是一种浏览器与服务器之间的通信协议，它可以在浏览器和服务器之间建立一个基于 HTTP 的双向通信通道。SockJS 的主要作用是提供一种 WebSocket 的兼容性解决方案，使得不支持 WebSocket 的浏览器也可以使用 WebSocket。

SockJS 实现了一个 WebSocket 的兼容层，它可以在浏览器和服务器之间建立一个基于 HTTP 的通信通道，然后通过这个通道进行双向通信。当浏览器不支持 WebSocket 时，SockJS 会自动切换到使用轮询（polling）或长轮询（long-polling）的方式进行通信

相关资料：http://jmesnil.net/stomp-websocket/doc/

## 为什么RabbitMQ作为STOMP消息代理

- spring 是基于内存的，对 STOMP指令简单模拟
- RabbitMQ 支持特性多，AMQP、STOMP、JMS、MQTT
- 支持集群部署

在 RabbitMQ 中合法的目的前缀：/temp-queue, /exchange, /topic, /queue, /amq/queue, /reply-queue/

rabbitmq需要开启rabbitmq_stomp插件


## 访问地址

http://localhost:8080/?userId=admin

```bash
mvn clean compile
mvn clean package
```


