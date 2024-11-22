package netty.websocket;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 叽哒嘎叽
 * @since 2024/11/15
 */
public class HeaderHandler extends ChannelInboundHandlerAdapter {
    public static final Logger log = LoggerFactory.getLogger(HeaderHandler.class);
    @Override
    public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String token = request.headers().get("token");
            log.info("request token: {}", token);
        }

        super.channelRead(ctx, msg);
    }
}
