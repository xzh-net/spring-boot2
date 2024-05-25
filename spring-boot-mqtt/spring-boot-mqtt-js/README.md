# 基于*MQTT*.js整合MQTT

Rabbitmq和Emqx已测试通过，如果使用RabbitMq需要开启插件`rabbitmq-plugins enable rabbitmq_web_mqtt`

> 使用mqtt.js最新版本5.6.2存在无法连接的问题。

降低版本解决：https://unpkg.com/mqtt@5.6.1/dist/mqtt.min.js

```bash
mvn clean compile
mvn clean package
```

