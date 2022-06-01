package net.xzh.mq.receive;

import java.io.FileOutputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import net.xzh.mq.domain.User;

/**
 * 用于监听消息类（既可以用于队列的监听，也可以用于主题监听）
 */
@Component
public class JmsReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsReceiver.class);
	
    /**
     * 接收TextMessage的方法
     */
    @JmsListener(destination = "${activemq.qname}")
    public void receiveTextMessage(Message message){
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage)message;
            try {
                LOGGER.info("接收Text消息：{}",textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    @JmsListener(destination = "${activemq.qname}")
    public void receiveMapMessage(Message message){
        if(message instanceof MapMessage){
            MapMessage mapMessage = (MapMessage)message;
            try {
            	LOGGER.info("接收Map消息：{},{}",mapMessage.getString("name"),mapMessage.getString("age"));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "${activemq.qname}")
    public void receiveObjectMessage(Message message){
        if(message instanceof ObjectMessage){
            ObjectMessage objectMessage = (ObjectMessage)message;
            try {
                User user = (User)objectMessage.getObject();
                LOGGER.info("接收Object消息：{}",user.toString());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }


    @JmsListener(destination = "${activemq.qname}")
    public void receiveBytesMessage(Message message){
        if(message instanceof BytesMessage){
            BytesMessage bytesMessage = (BytesMessage)message;
            try {
                LOGGER.info("接收Bytes消息：{}",bytesMessage.getBodyLength());
                //1.设计缓存数组
                byte[] buffer = new byte[(int)bytesMessage.getBodyLength()];
                //2.把字节消息的内容写入到缓存数组
                bytesMessage.readBytes(buffer);
                //3.构建文件输出流
                FileOutputStream outputStream = new FileOutputStream("d:/xzh.jpg");
                //4.把数据写出本地硬盘
                outputStream.write(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "${activemq.qname}")
    public void receiveStreamMessage(Message message){
        if(message instanceof StreamMessage){
            StreamMessage streamMessage = (StreamMessage)message;
            try {
                //接收消息属性
            	LOGGER.info("接收Stream消息：{},{},{}",streamMessage.getStringProperty("订单"),streamMessage.readString(),streamMessage.readInt());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
