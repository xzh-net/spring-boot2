package net.xzh.chat.server;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 处理webSocket请求连接
 * @author Kevin
 * @date 2020/12/25 17:24
 *
 */
public class SocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);
	
    /*消息处理器*/
    private static MessageProcess messageProcess=new MessageProcess();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        LOGGER.info("聊天消息内容,{}",msg.text());
        String clientMsg = msg.text();
        new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + clientMsg);
        messageProcess.process(ctx,msg);
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //用户上线
        messageProcess.login(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //用户下线
        messageProcess.logout(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("发生异常,{}",cause.getMessage());
        cause.printStackTrace();
    }

}
