package net.xzh.log.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @Author xzh
 * 线程池配置
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {

    // 核心线程数
    private int corePoolSize = 50;
    // 最大线程数
    private int maxPoolSize = 200;
    // 队列最大长度
    private int queueCapacity = 1000;
    // 设置非核心线程超时回收时间
    private int keepAliveSeconds = 300;

    @Override
    public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);// 设置核心线程数
		executor.setMaxPoolSize(maxPoolSize);// 最大线程数
		executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);// 设置非核心线程超时回收时间
		executor.setThreadNamePrefix("thread-default");// 设置默认线程名称
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

}
