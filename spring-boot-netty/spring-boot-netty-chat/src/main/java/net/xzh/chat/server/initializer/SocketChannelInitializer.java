package net.xzh.chat.server.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import net.xzh.chat.server.handler.SocketHandler;

/**
 * 处理器通道初始化
 * @author Kevin
 * @date 2020/12/25 13:32
 *
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 1. HTTP编解码器 - 将HTTP请求解码/编码
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        
        // 2. HTTP消息聚合器 - 将分段HTTP消息聚合成完整消息
        pipeline.addLast("httpObjectAggregator",new HttpObjectAggregator(10));

        // 3. WebSocket协议处理器 - 处理WebSocket握手和协议
        pipeline.addLast("webSocketProtocol",new WebSocketServerProtocolHandler("/ws"));

        // 4. 自定义业务处理器 - 处理具体聊天业务
        pipeline.addLast("webSocketHandler",new SocketHandler());
    }
}
