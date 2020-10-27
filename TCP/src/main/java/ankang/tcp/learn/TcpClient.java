package ankang.tcp.learn;

import java.io.IOException;
import java.net.Socket;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-23
 */
public class TcpClient {

    public static void main(String[] args) throws IOException {
        try (final Socket socket = new Socket("localhost" , 6969)) {
            while (true) {
                System.out.println("====================== send data ===========================");
                socket.getOutputStream().write("客户端请求\n".getBytes());
                socket.getOutputStream().flush();
            }
        }
    }

}
