package nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/18
 */
public class NIOTest {
    public static void main(String[] args) throws IOException {
        // 创建选择器
        Selector selector = Selector.open();

        // 将通道注册到选择器上
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        // 获取到达的事件
        while (true) {
            // 监听事件
            int num = selector.select();

            // 获取到达的事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // 处理事件
                } else if (key.isReadable()) {
                    // 处理事件
                }
                keyIterator.remove();
            }
        }
    }
}
