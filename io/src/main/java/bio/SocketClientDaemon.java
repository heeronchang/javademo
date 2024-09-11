package bio;

import java.util.concurrent.CountDownLatch;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/11
 */
public class SocketClientDaemon {

    public static void main(String[] args) throws InterruptedException {
        Integer clientNumber = 16;
        CountDownLatch countDownLatch = new CountDownLatch(clientNumber);
        for (int index = 0; index < clientNumber; index++, countDownLatch.countDown()) {
            SocketClientRequestThread clientRequestThread = new SocketClientRequestThread(countDownLatch, index);
            new Thread(clientRequestThread).start();
        }

        synchronized (SocketClientDaemon.class) {
            SocketClientDaemon.class.wait();
        }
    }
}
