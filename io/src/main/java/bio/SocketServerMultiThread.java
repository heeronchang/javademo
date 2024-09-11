package bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/11
 */
@Slf4j
public class SocketServerMultiThread implements Runnable {
    private static final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(
                    4,
                    8,
                    5L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(8));

    private Socket socket;

    public SocketServerMultiThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();

            Integer sourcePort = socket.getPort();
            int maxLen = 1024;
            byte[] contextBytes = new byte[maxLen];
            //使用线程，同样无法解决read方法的阻塞问题，
            //也就是说read方法处同样会被阻塞，直到操作系统有数据准备好
            int realLen = in.read(contextBytes, 0, maxLen);
            //读取信息
            String message = new String(contextBytes, 0, realLen);
            log.info("服务器收到来自于端口: {} 的信息: {}", +sourcePort, message);

            out.write(("[" + message + "]" + "的服务器回应信息").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                SocketServerMultiThread socketServerMultiThread = new SocketServerMultiThread(socket);
                threadPoolExecutor.execute(socketServerMultiThread);
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
