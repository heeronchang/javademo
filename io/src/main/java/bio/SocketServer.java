package bio;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/9
 */
@Slf4j
public class SocketServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8081);
        try {
            while (true) {
                Socket socket = serverSocket.accept();

                Integer sourcePort = socket.getPort();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                int maxLength = 1024;
                byte[] bytes = new byte[maxLength];
                int readLength = in.read(bytes, 0, maxLength);
                String message = new String(bytes, 0, readLength);
                log.info("接收到来自客户端({})的信息: {}", sourcePort, message);

                out.write(("[" + message + "]"+"的服务器回应信息").getBytes());
                out.close();
                in.close();
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
