package net.xzh.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadService.class);
	
	public void test() {
		LOGGER.info("同步,{}",Thread.currentThread().getName());
	}

	@Async
	public void asyncTest() {
		LOGGER.info("异步默认,{}",Thread.currentThread().getName());
	}

	@Async("define")
	public void asyncExampleTest() {
		LOGGER.info("异步define,{}",Thread.currentThread().getName());
	}

}
