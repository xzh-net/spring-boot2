package net.xzh.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<Protocol> {

	private static final ByteBuf HEART_BEAT = Unpooled
			.unreleasableBuffer(Unpooled.copiedBuffer(new Protocol(1234567890L, "已被下线").toString(), CharsetUtil.UTF_8));

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				// 服务器10s没有收到客户端心跳
				log.info("{},读空闲10s触发,关闭连接", ctx.channel().remoteAddress());
				ctx.writeAndFlush(HEART_BEAT).addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						// TODO Auto-generated method stub
						ctx.channel().close();
					}
				});
			} else if (event.state() == IdleState.WRITER_IDLE) {

			} else if (event.state() == IdleState.ALL_IDLE) {

			}

			super.userEventTriggered(ctx, evt);
		}
	}

	/**
	 * 新客户端接入
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("{},连接成功", ctx.channel().remoteAddress());
	}

	/**
	 * 客户端断开
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("{},断开连接", ctx.channel().remoteAddress());
		SocketHolder.remove((NioSocketChannel) ctx.channel());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Protocol protocol) throws Exception {
		log.info("收到客户端={},{}", ctx.channel().remoteAddress(), protocol);
		// 保存客户端与 Channel 之间的关系
		SocketHolder.put(protocol.getId(), (NioSocketChannel) ctx.channel());
	}

	/**
	 * 异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}