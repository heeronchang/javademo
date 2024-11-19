package nio.multiwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程版服务器
 * 一个 Selector 用来监听连接，一个（创建两个Worker则是两个，round robin 轮询） Selector 用来监听读写事件
 * @author 叽哒嘎叽
 * @since 2024/11/19
 */
public class MultiThreadServer {
    private static final Logger log = LoggerFactory.getLogger(MultiThreadServer.class);

    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("main");

        // 用来接收连接的 channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 接收连接的channel 的选择器
        Selector selector = Selector.open();
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);
//        key.interestOps(SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(8888));

        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }

        AtomicInteger index = new AtomicInteger();

        // 保证 selector 能一直监听
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            // 遍历本次循环所有 SelectionKey
            while (iterator.hasNext()) {
                // 拿到本次 SelectionKey
                SelectionKey acceptKey = iterator.next();
                // 处理完 需要从集合中移除
                iterator.remove();
                // 判断拿到的事件是否为可接受连接事件
                if (acceptKey.isAcceptable()) {
                    // 获取新建立连接的 channel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    log.debug("connected-{}", socketChannel.getRemoteAddress());
                    // 把新建立连接的 channel 交个 worker 线程处理
                    workers[index.getAndIncrement() % workers.length].register(socketChannel);
                    log.debug("registered-{}", socketChannel.getRemoteAddress());
                }
            }
        }
    }
}

class Worker implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Worker.class);

    private final String name;
    private Selector selector;
    private Thread thread;
    private volatile boolean start;

    public Worker(String name) {
        this.name = name;
    }

    public void register(SocketChannel socketChannel) throws IOException {
        if (!start) {
            selector = Selector.open();
            thread = new Thread(this, name);
            thread.start();
            start = true;
        }

        // 线程启动后，在 run 方法中执行 selector.select() 会阻塞，直到有新的事件发生，才会返回
        // 此处调用一次 selector.wakeup()，唤醒线程，让线程从阻塞中返回，继续执行下面的代码，完成事件的注册
        selector.wakeup();
        // 把 socketChannel 注册到 selector 上，并监听可读事件
        socketChannel.register(selector, SelectionKey.OP_READ, null);
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        log.debug("readable-{}", key.channel());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
