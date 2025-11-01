# AMPQ

## 工作模式

1. 简单模式：一个生产者，只能有一个消费者。（自动确认和手动确认示例）
2. 工作模式：也称为负载均衡模式，一个生产者，多个消费者，默认策略是轮询。（获取消息原始属性示例）
3. 订阅模式 - 扇形交换机：一个生产者，多个消费者， 队列的消费者都能拿到消息，实现一条消息被多个消费者消费。
4. 路由模式 - 直接交换机：比Fanout增加了路由键，根据键规则，适用于需要将不同消息路由到不同队列的场景。
5. 主题模式 - 主题交换机：交换机和队列绑定的时候路由键支持通配符形式。
      - `#` ：匹配一个或多个词
      - `*` ：匹配不多不少恰好1个词



## 安装Rabbitmq

```bash
docker run -p 5672:5672 -p 61613:61613 -p 15672:15672 --name rabbitmq -d rabbitmq:3.7.15
```

### 开启管理插件

```bash
docker exec -it rabbitmq /bin/bash
rabbitmq-plugins enable rabbitmq_management
```

### 添加用户、虚拟主机、授权

```bash
rabbitmqctl add_user admin 123456
rabbitmqctl set_user_tags admin administrator
rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"
rabbitmqctl add_vhost /xzh
rabbitmqctl set_permissions -p "/xzh" admin ".*" ".*" ".*"
```

### 访问地址

http://172.17.17.161:15672/