package net.xzh.netty.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Client {
	private EventLoopGroup work = new NioEventLoopGroup();
	@Value("${netty.server.port}")
	private int nettyPort;
	@Value("${netty.server.host}")
	private String host;

	@PostConstruct
	public void start() {
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(work)//
					.channel(NioSocketChannel.class)// 设置工厂
					.handler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(new IdleStateHandler(0, 5, 0))// 5 秒没发送消息触发
									.addLast(new HeartbeatEncode())// 自定义解码器
									.addLast(new ClientHandle());// 处理类
						}
					});
			ChannelFuture connect = bootstrap.connect(host, nettyPort).sync();
			if (connect.isSuccess()) {
				log.info("客户端已连接{},{}", host, nettyPort);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 销毁
	 */
	@PreDestroy
	public void destroy() {
		work.shutdownGracefully().syncUninterruptibly();
		log.info("Client关闭");
	}
}