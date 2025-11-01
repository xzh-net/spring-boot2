package net.xzh.rabbitmq.listener.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.common.constant.CommonConstant;
import net.xzh.rabbitmq.config.exchange.DirectExchangeConfig;

/**
 * 路由模式监听器
 */
@Service
public class DirectExchangeListener {

    private static final Logger log = LoggerFactory.getLogger(DirectExchangeListener.class);

    /**
     * 监听直接队列1 - 处理error和warn级别的消息
     * 路由键：[error, warn]
     */
    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE_1)
    public void handleErrorAndWarnMessages(String message) {
    	log.info("{} - 队列1[{}|{}]接收到消息: {}",
                CommonConstant.EXCHANGE_DIRECT,
                DirectExchangeConfig.ROUTING_KEY_ERROR,
                DirectExchangeConfig.ROUTING_KEY_WARN,
                message);
        // 这里可以添加业务处理逻辑
    	processDirectMessage(message, "ERROR/WARN");
    }

    /**
     * 监听直接队列2 - 处理info和debug级别的消息
     * 路由键：[info, debug]
     */
    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE_2)
    public void handleInfoAndDebugMessages(String message) {
    	log.info("{} - 队列2[{}|{}]接收到消息: {}",
                CommonConstant.EXCHANGE_DIRECT,
                DirectExchangeConfig.ROUTING_KEY_INFO,
                DirectExchangeConfig.ROUTING_KEY_DEBUG,
                message);
        // 这里可以添加业务处理逻辑
    	processDirectMessage(message, "INFO/DEBUG");
    }

    /**
     * 处理直接交换机消息的公共方法
     * 
     * @param message 消息内容
     * @param level 消息级别，用于区分处理逻辑
     */
    private void processDirectMessage(String message, String level) {
        try {
            // 记录调试信息
            log.debug("开始处理{}级别的直接交换机消息: {}", level, message);
            
            // 这里实现具体的业务逻辑
            // 根据不同的消息级别，可以有不同的处理策略
            
            switch (level) {
                case "ERROR/WARN":
                    // 处理错误和警告级别的业务逻辑
                    // errorWarnService.process(message);
                    processErrorAndWarnMessage(message);
                    break;
                case "INFO/DEBUG":
                    // 处理信息和调试级别的业务逻辑
                    // infoDebugService.process(message);
                    processInfoAndDebugMessage(message);
                    break;
                default:
                    // 默认处理逻辑
                    // defaultDirectService.process(message);
                    processDefaultMessage(message);
                    break;
            }
            
            log.debug("成功处理{}级别的直接交换机消息", level);
            
        } catch (Exception e) {
            log.error("处理{}级别的直接交换机消息时发生错误, message: {}", level, message, e);
            // 可以根据业务需求决定是否抛出异常
            // 对于直接交换机，可以根据错误类型决定是否重试或进入死信队列
        }
    }

    /**
     * 处理错误和警告级别消息的具体逻辑
     */
    private void processErrorAndWarnMessage(String message) {
        // 错误和警告级别消息通常需要特殊处理
        // 例如：发送告警、记录详细日志、通知相关人员等
        
        log.info("处理错误/警告消息: {}", message);
        
        // 示例处理逻辑：
        // 1. 记录到错误日志系统
        // errorLogService.record(message);
        
        // 2. 发送告警通知
        // alertService.sendAlert(message);
        
        // 3. 进行错误分析和统计
        // errorAnalysisService.analyze(message);
    }

    /**
     * 处理信息和调试级别消息的具体逻辑
     */
    private void processInfoAndDebugMessage(String message) {
        // 信息和调试级别消息通常是正常的业务日志
        // 例如：记录操作日志、统计信息、调试信息等
        
        log.info("处理信息/调试消息: {}", message);
        
        // 示例处理逻辑：
        // 1. 记录到业务日志系统
        // businessLogService.record(message);
        
        // 2. 更新统计信息
        // statisticsService.update(message);
        
        // 3. 调试信息处理
        // debugService.process(message);
    }

    /**
     * 默认消息处理逻辑
     */
    private void processDefaultMessage(String message) {
        // 默认的消息处理逻辑
        log.info("处理默认消息: {}", message);
        
        // 通用的消息处理逻辑
        // defaultMessageProcessor.process(message);
    }
}