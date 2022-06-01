package net.xzh.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class HeartbeatEncode extends MessageToByteEncoder<Protocol> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
		out.writeLong(msg.getId());
		out.writeBytes(msg.getContent().getBytes());
	}
}