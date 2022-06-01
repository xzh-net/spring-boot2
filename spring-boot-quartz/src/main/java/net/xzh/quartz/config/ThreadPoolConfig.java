package net.xzh.quartz.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置 AsyncConfigurer的两个方法是@Async默认线程池的配置
 */
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);// 设置核心线程数
		executor.setMaxPoolSize(128);// 最大线程数
        executor.setKeepAliveSeconds(60);// 设置非核心线程超时回收时间
		executor.setThreadNamePrefix("thread-default-");// 设置默认线程名称
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

	/**
	 * 自定义线程池样例
	 * 
	 * @return
	 */
	@Bean("define")
	public Executor define() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);// 设置核心线程数
		executor.setMaxPoolSize(128);// 最大线程数
		executor.setKeepAliveSeconds(60);// 设置非核心线程超时回收时间
		executor.setThreadNamePrefix("thread-define-");// 设置默认线程名称
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 拒绝策略
		// 等待所有任务结束后再关闭线程池
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}
}
