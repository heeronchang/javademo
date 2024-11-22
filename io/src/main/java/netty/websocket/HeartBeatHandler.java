package netty.websocket;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳检测处理器
 *
 * @author 叽哒嘎叽
 * @since 2024/11/15
 */
public class HeartBeatHandler extends ChannelDuplexHandler {
    private static final Logger log = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("read idle");
                ctx.close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("write idle");
                ctx.writeAndFlush("heart beat idle");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
