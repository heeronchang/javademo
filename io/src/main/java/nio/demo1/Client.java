package nio.demo1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 叽哒嘎叽
 * @since 2024/9/18
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream out = socket.getOutputStream();
        out.write("hello world".getBytes());
        out.close();
    }
}
