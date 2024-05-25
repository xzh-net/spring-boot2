# 基于Paho整合MQTT

1. 发布 /mqtt/pub?topic=testtopic&message=hello
2. 订阅 /mqtt/sub?topic=test2
3. 取消订阅 /mqtt/unsub?topic=test2


```bash
mvn clean compile
mvn clean package
```