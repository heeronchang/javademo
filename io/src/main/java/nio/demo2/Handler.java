package nio.demo2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/23
 */
public class Handler implements Runnable {
    private volatile static Selector selector;
    private final SocketChannel channel;
    private SelectionKey key;
    private volatile ByteBuffer input = ByteBuffer.allocate(1024);
    private volatile ByteBuffer output = ByteBuffer.allocate(1024);
    
    public Handler(SocketChannel channel) throws IOException {
        this.channel = channel;
        channel.configureBlocking(false); // 设置客户端连接为非阻塞模式
        selector = Selector.open(); // 为客户端创建一个新的多路复用器
        key = channel.register(selector, SelectionKey.OP_READ); // 注册客户端的读事件
    }
    @Override
    public void run() {
        try {
            while (selector.isOpen() && channel.isOpen()) {
                Set<SelectionKey> keys = selector.selectedKeys(); // 等待客户端事件发生
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    // 处理读事件
                    if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) throws IOException {
        channel.read(input);
        if (input.position() == 0) {
            return;
        }

        input.flip();
        process(); // 读取的数据进行业务处理
        input.clear();
        key.interestOps(SelectionKey.OP_WRITE); // 读取完成后监听写事件
    }

    private void write(SelectionKey key) throws IOException {
        output.flip();
        if (channel.isOpen()) {
            channel.write(output); // 当有写入事件时，将业务处理的结果写入到客户端channel中
            key.channel();
            channel.close();
            output.clear();
        }
    }

    private void process() {
        byte[] bytes = new byte[input.remaining()];
        input.get(bytes);
        String message = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("receive message from client: " + message);

        output.put("hello client".getBytes(StandardCharsets.UTF_8));
    }
}
