package ankang.tomcat.http;

import ankang.tomcat.server.Servlet;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-16
 */
public class RequestProcessor implements Runnable {

    private Socket socket;
    private Map<String, Servlet> servletMap;

    public RequestProcessor(Socket socket , Map<String, Servlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }

    @SneakyThrows
    @Override
    public void run() {

        // 使用输入流获传输信息
        final InputStream inputStream = socket.getInputStream();

        final Request request = new Request(inputStream);
        final Response response = new Response(socket.getOutputStream());

        // 处理静态资源
        final Servlet servlet = servletMap.get(request.getUrl());
        if (servlet == null) {
            response.outputHtml(request.getUrl());
        } else {
            // 处理动态资源
            servlet.service(request , response);
        }

    }
}
