package net.xzh.netty.server;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class HeartbeatDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		long id = in.readLong();
		byte[] bytes = new byte[in.readableBytes()];
		in.readBytes(bytes);
		String content = new String(bytes);
		Protocol protocol = new Protocol(id, content);
		out.add(protocol);
	}
}