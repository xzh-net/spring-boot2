# AMPQ

访问地址：http://127.0.0.1:8080/doc.html

1. 简单模式，一个生产者，只能有一个消费者。
2. 工作模式，也称为负载均衡模式，一个生产者，多个消费者，默认策略是轮询。
3. 订阅模式-Fanout，一个生产者，多个消费者， 队列的消费者都能拿到消息，实现一条消息被多个消费者消费。
4. 路由模式-Direct，比Fanout增加了路由键，根据键规则，适用于需要将不同消息路由到不同队列的场景。
5. 主题模式-Topic，交换机和队列绑定的时候路由键支持通配符形式。
      - `#` ：匹配一个或多个词
      - `*` ：匹配不多不少恰好1个词


```bash
mvn clean compile
mvn clean package
```