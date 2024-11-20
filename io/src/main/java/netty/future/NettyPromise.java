package netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * netty promise usage
 * @author 叽哒嘎叽
 * @since 2024/11/19
 */
public class NettyPromise {
    private static final Logger log = LoggerFactory.getLogger(NettyPromise.class);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建 EventLoop
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 创建promise
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            log.debug("calculating");
            try {
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();

        log.debug("waiting...");
        promise.get();
    }
}
