package nio.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/20
 */
public class Reactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open(); // 创建服务端的SocketChannel
        serverSocketChannel.configureBlocking(false); // 设置为非阻塞模式

        selector = Selector.open(); // 创建一个Selector多路复用器
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将ServerSocketChannel注册到Selector上，并设置监听事件为OP_ACCEPT
        serverSocketChannel.bind(new InetSocketAddress(port)); // 服务端端口绑定
        key.attach(new Acceptor(serverSocketChannel)); // 为服务端的SocketChannel注册一个Acceptor对象，用于处理客户端的连接请求
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select(); // 阻塞等待事件发生
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next()); // 监听到客户端连接时间后，将其分发给Acceptor进行处理
                    iterator.remove();
                }

                selector.selectNow();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) throws IOException {
        Runnable r = (Runnable) key.attachment();
        if (r != null) {
            r.run();
        }
    }
}
