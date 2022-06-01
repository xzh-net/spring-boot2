# RocketMq（SendSms）

访问地址：http://127.0.0.1:8080/doc.html 

1. 同步消息,ack处理方式：throw new RuntimeException("模拟抛出的异常");
2. 异步
3. 单向消息
4. 单向顺序
5. 延迟消息
6. 消息异常重新投递处理源码

DefaultRocketMQListenerContainer.java

```java
public class DefaultMessageListenerConcurrently implements MessageListenerConcurrently {

    @SuppressWarnings("unchecked")
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt messageExt : msgs) {
            log.debug("received msg: {}", messageExt);
            try {
                long now = System.currentTimeMillis();
                rocketMQListener.onMessage(doConvertMessage(messageExt));
                long costTime = System.currentTimeMillis() - now;
                log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
            } catch (Exception e) {
                log.warn("consume message failed. messageExt:{}", messageExt, e);
                context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
```

7. 消息重试

```java
public class MessageListenerImpl implements MessageListener {
    @Override
    public Action consume(Message message, ConsumeContext context) {
        //处理消息
        doConsumeMessage(message);
        //方式1：返回 Action.ReconsumeLater，消息将重试
        return Action.ReconsumeLater;
        //方式2：返回 null，消息将重试
        return null;
        //方式3：直接抛出异常， 消息将重试
        throw new RuntimeException("Consumer Message exceotion");
    }
}
```

失败不重试

```java
public class MessageListenerImpl implements MessageListener {
    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            doConsumeMessage(message);
        } catch (Throwable e) {
            //捕获消费逻辑中的所有异常，并返回 Action.CommitMessage;
            return Action.CommitMessage;
        }
        //消息处理正常，直接返回 Action.CommitMessage;
        return Action.CommitMessage;
    }
}
```

```bash
mvn clean compile
mvn clean package
```