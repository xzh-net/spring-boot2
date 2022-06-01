package net.xzh.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandle extends SimpleChannelInboundHandler<ByteBuf> {
	// HEART_BEAT可以采用注册方式从容器中获取
	// 需要注意当主线程启动后阻塞导致无法通过ApplicationContextAware方式获取上下文
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
			if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
				// 向服务端发送消息
				Protocol protocol = SpringBeanFactory.getBean(Protocol.class);
				log.info("每5秒向服务器{},{}", ctx.channel().remoteAddress(), protocol);
				ctx.writeAndFlush(protocol).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
			}
		}
		super.userEventTriggered(ctx, evt);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		// 从服务端收到消息时被调用
		log.info("收到服务器={},{}", ctx.channel().remoteAddress(), in.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}