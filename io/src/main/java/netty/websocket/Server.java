package netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.NettyServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * websocket base netty 服务端
 * @author 叽哒嘎叽
 * @since 2024/11/11
 */
public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

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
                .handler(new LoggingHandler(LogLevel.DEBUG)) // 设置日志级别
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec()); // 配置http协议支持
                        pipeline.addLast(new HttpObjectAggregator(1024  * 64)); // 聚合http请求
                        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new HeartBeatHandler());
                        pipeline.addLast(new HeaderHandler());
                        // 升级为websocket协议
                        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536, true, true));
                        pipeline.addLast(new ServerHandler());
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
