package net.xzh.netty.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Server {
	private EventLoopGroup boss = new NioEventLoopGroup();
	private EventLoopGroup work = new NioEventLoopGroup();
	@Value("${netty.server.port}")
	private int port;

	/**
	 * 启动 Netty
	 *
	 * @return
	 * @throws InterruptedException
	 */
	@PostConstruct
	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		try {
			bootstrap.group(boss, work)// 设置线程池
					.channel(NioServerSocketChannel.class)// 设置socket工厂
					.option(ChannelOption.SO_BACKLOG, 2048)// 服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128
					.childOption(ChannelOption.SO_KEEPALIVE, true)// 保持链接活跃，心跳必须开启，默认关闭
					.childOption(ChannelOption.TCP_NODELAY, true)// 默认禁用，最小化报文传输延时
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(new IdleStateHandler(10, 0, 0))// 设置心跳
									.addLast(new HeartbeatDecoder())// 自定义解码器
									.addLast(new ServerHandler());// 处理器
						}
					});
			ChannelFuture future = bootstrap.bind(port).sync();
			if (future.isSuccess()) {
				log.info("服务器{}已启动", port);
			}
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 销毁
	 */
	@PreDestroy
	public void destroy() {
		boss.shutdownGracefully().syncUninterruptibly();
		work.shutdownGracefully().syncUninterruptibly();
		log.info("Server关闭");
	}
}
