package net.xzh.minio.listener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
 
@Component
public class DirectExchangeListener {
    @RabbitListener(queues = "#{bucketqueue}")// 监听指定的队列
    public void receive(String message) {
        System.out.println("Received: " + message); // 处理接收到的消息
    }
}