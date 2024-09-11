package bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/8
 */
@Slf4j
public class SocketClientRequestThread implements Runnable {

    private CountDownLatch countDownLatch;

    /**
     * 客户端序号
     */
    private Integer clientIndex;

    public SocketClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
        this.countDownLatch = countDownLatch;
        this.clientIndex = clientIndex;
    }

    @Override
    public void run() {
        Socket socket = null;
        OutputStream clientRequest = null;
        InputStream clientResponse = null;

        try {
            socket = new Socket("localhost", 8081);
            clientRequest = socket.getOutputStream();
            clientResponse = socket.getInputStream();

            countDownLatch.await();
            clientRequest.write(("这是第" + this.clientIndex + " 个客户端的请求。").getBytes());
            clientRequest.flush();
            log.info("第{}个客户端的请求发送完成，等待服务器返回信息", this.clientIndex);

            int maxLength = 1024;
            byte[] bytes = new byte[maxLength];
            int readLength;
            String message = "";
            while ((readLength = clientResponse.read(bytes, 0, maxLength)) != -1) {
                message += new String(bytes, 0, readLength);
            }
            log.info("接收到来自服务器的信息: {}", message);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (clientRequest != null) {
                    clientRequest.close();
                }
                if (clientResponse != null) {
                    clientResponse.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
