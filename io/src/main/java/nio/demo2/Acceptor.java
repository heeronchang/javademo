package nio.demo2;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/23
 */
public class Acceptor implements Runnable {
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final ServerSocketChannel serverSocketChannel;

    public Acceptor(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept(); // 获取新连接
            if (null != socketChannel) {
                executorService.execute(new Handler(socketChannel)); // 启动一个线程来处理新连接
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
