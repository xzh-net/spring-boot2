package net.xzh.chat.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*
 * 聊天服务
 * @author Kevin
 * @date 2020/12/25 12:02
 *
 */
public class ChatServer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatServer.class);

    /**
     * 启动服务方法
     * @param host 地址
     * @param port 端口
     */
    public static void serverStart(final String host,final int port){
        serverStart0(host,port);
    }

    /**
     * 私有启动服务方法
     * @param host 地址
     * @param port 端口
     */
    private static void serverStart0(final String host,final int port){
        //实例化两个循环事件组 bossGroup workerGroup
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            //实例化服务器启动器
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SocketChannelInitializer());
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            LOGGER.info("服务已启动");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            LOGGER.error("服务启动异常信息,{}",e.getMessage());
        }finally{
            // 优雅关闭事件循环组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
