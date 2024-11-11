package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/11
 */
public class NettyServer {
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args) throws InterruptedException {
        // 1. 创建bossgroup线程组，处理网络连接时间，默认线程数量和电脑处理器相关，处理器线程数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 2. 创建workergroup线程组，处理网络读写事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 3. 创建服务端启动助手 serverBootStrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup) // 配置线程组
                .channel(NioServerSocketChannel.class) // 配置服务端通道实现为NIO 的TCP服务端channel
                .option(ChannelOption.SO_BACKLOG, 128) // 对于阻塞的连接队列大小的配置
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE) // 开启 keep-alive， 根据操作系统的设定保持长连接逼供检测连接可用性
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });

        // 启动服务并绑定端口，且把异步改成同步
        ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
        log.info("server booted.");
        // 关闭通道
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
