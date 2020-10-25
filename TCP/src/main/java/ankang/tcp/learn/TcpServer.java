package ankang.tcp.learn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-23
 */
public class TcpServer {

    public static void main(String[] args) throws IOException {
        try (final ServerSocket serverSocket = new ServerSocket(6969)) {
            final int port = serverSocket.getLocalPort();
            System.out.println(">>>>>>>>>>>>>>> port: " + port);

            while (true) {
                final Socket accept = serverSocket.accept();
                final BufferedReader br = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                System.out.println("============================== receive data ===================================");
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(">>>>>>>>>>>>>>> server received data: " + line);
                }

            }
        }
    }

}
