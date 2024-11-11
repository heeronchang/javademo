package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/11
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    public static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);
    @Override
    public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        log.info("server receive msg: {}", buf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(io.netty.channel.ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello from netty server!", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(io.netty.channel.ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught", cause);
        ctx.close();
    }
}
