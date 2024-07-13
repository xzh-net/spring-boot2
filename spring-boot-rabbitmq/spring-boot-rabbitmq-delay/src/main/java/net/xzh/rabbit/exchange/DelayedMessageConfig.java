package net.xzh.rabbit.exchange;

 
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import java.util.HashMap;
import java.util.Map;
 
/**
 * 延时队列 可实现不同间隔的延迟 彼此不受影响
 * 基于rabbitmq-delayed-message-exchange 插件完成
**/
@Configuration
public class DelayedMessageConfig {
	public static final String QUEUE_NAME = "delay_queue";

	public static final String EXCHANGE_NAME = "delay_exchange";

	/**
	 * 延迟队列
	 * @return
	 */
	@Bean
	Queue queue() {    
	      return new Queue(QUEUE_NAME, true);
	}

	// 定义一个延迟交换机
	@Bean
	CustomExchange delayExchange() {    
	    Map<String, Object> args = new HashMap<String, Object>();    
	    args.put("x-delayed-type", "direct");    
	    return new CustomExchange(EXCHANGE_NAME, "x-delayed-message", true, false, args);
	}

	// 绑定队列到这个延迟交换机上
	@Bean
	Binding DelayBinding(Queue queue, CustomExchange delayExchange) {    
	    return BindingBuilder.bind(queue).to(delayExchange).with(QUEUE_NAME).noargs();
	}
}