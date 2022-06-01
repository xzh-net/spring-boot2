package net.xzh.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * 初始化WebSocketChannel
 * @author Kevin
 * @date 2020/12/25 13:32
 *
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //因为基于http协议，使用http的编码和解码器
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        //是以块的方式写，添加ChunkedWriteHandler处理器 (可以省略不用)
       // pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
        //http数据在传输过程中是分段,HttpObjectAggregator，就是可以将多个段聚合
        pipeline.addLast("httpObjectAggregator",new HttpObjectAggregator(10));
        //自定义编码解码器
      //  pipeline.addLast("chatDecoderHandler",new ChatEncoderHandler());
       // pipeline.addLast("chatEncoderHandler",new ChatEncoderHandler());

        pipeline.addLast("",new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast("webSocketHandler",new SocketHandler());
    }
}
