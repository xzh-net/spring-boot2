package net.xzh.mq.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.mq.common.model.CommonResult;
import net.xzh.mq.domain.User;

/**
 * 原生api
 * @author CR7
 *
 */
@Api(tags = "jms原生Api")
@RestController
public class JmsController {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${activemq.qname}")
    private String qname;

	@ApiOperation("发送TextMessage消息")
	@RequestMapping(value = "/sendTextMessage", method = RequestMethod.GET)
	public CommonResult<Object> sendTextMessage(@RequestParam String msg) {
		jmsTemplate.send(qname, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(msg);
				return textMessage;
			}
		});
		return CommonResult.success(System.currentTimeMillis());
	}

	/**
	 * 发送MapMessage消息
	 */
	@ApiOperation("发送MapMessage消息")
	@Transactional
	@RequestMapping(value = "/sendMapMessage", method = RequestMethod.GET)
	public CommonResult<Object> sendMapMessage(@RequestParam String msg) {
		jmsTemplate.send(qname, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("name", "张三");
				mapMessage.setInt("age", 20);

				return mapMessage;
			}
		});
		return CommonResult.success(System.currentTimeMillis());
	}

	/**
	 * 发送ObjectMessage消息
	 */
	@ApiOperation("发送ObjectMessage消息")
	@RequestMapping(value = "/sendObjectMessage", method = RequestMethod.GET)
	public CommonResult<Object> sendObjectMessage(@RequestParam String msg) {
		jmsTemplate.send(qname, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				User user = new User("xzh", 29);
				ObjectMessage objectMessage = session.createObjectMessage(user);
				return objectMessage;
			}
		});
		return CommonResult.success(System.currentTimeMillis());
	}

	/**
	 * 发送BytesMessage消息
	 */
	@ApiOperation("发送BytesMessage消息")
	@RequestMapping(value = "/sendBytesMessage", method = RequestMethod.GET)
	public CommonResult<Object> sendBytesMessage(@RequestParam String msg) {
		jmsTemplate.send(qname, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				BytesMessage bytesMessage = session.createBytesMessage();
				// 1.读取文件
				FileInputStream inputStream = null;
				File file = new File("d:/a.png");
				// 2.构建文件输入流
				try {
					inputStream = new FileInputStream(file);
					// 3.把文件流写入到缓存数组中
					byte[] buffer = new byte[(int) file.length()];
					inputStream.read(buffer);
					// 4.把缓存数组写入到BytesMessage中
					bytesMessage.writeBytes(buffer);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return bytesMessage;
			}
		});
		return CommonResult.success(System.currentTimeMillis());
	}

	/**
	 * 发送StreamMessage消息
	 */
	@ApiOperation("发送StreamMessage消息")
	@RequestMapping(value = "/sendStreamMessage", method = RequestMethod.GET)
	public CommonResult<Object> sendStreamMessage(@RequestParam String msg) {
		jmsTemplate.send(qname, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				StreamMessage streamMessage = session.createStreamMessage();
				streamMessage.writeString("hello，火锅");
				streamMessage.writeInt(20);
				// 设置消息属性：标记、过滤
				streamMessage.setStringProperty("订单", "123456789A01");

				return streamMessage;
			}
		});
		return CommonResult.success(System.currentTimeMillis());
	}

}