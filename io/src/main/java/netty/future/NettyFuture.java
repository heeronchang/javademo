package netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * netty future usage
 *
 * @author 叽哒嘎叽
 * @since 2024/11/19
 */
public class NettyFuture {
    private static Logger log = LoggerFactory.getLogger(NettyFuture.class);
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        NioEventLoopGroup group = new NioEventLoopGroup();

        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("计算中...");
                Thread.sleep(1000);
                return 1;
            }
        });

        log.debug("waiting calculate");
//        log.debug("result: {}", future.get()); // 同步方式获取

        future.addListener(f -> log.debug("result: {}", f.getNow()));
    }
}
