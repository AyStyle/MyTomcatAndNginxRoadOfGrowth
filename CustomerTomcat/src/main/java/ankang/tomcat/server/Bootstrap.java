package ankang.tomcat.server;

import ankang.tomcat.http.Request;
import ankang.tomcat.http.RequestProcessor;
import ankang.tomcat.http.Response;
import ankang.tomcat.utils.HttpProtocolUtil;
import lombok.Getter;
import lombok.Setter;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-14
 */
public class Bootstrap {

    /**
     * Socket监听的端口号
     */
    @Getter
    @Setter
    private int port = 8080;

    private Map<String, Servlet> servletMap = new HashMap<>(16);

    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * Tomcat启动时需要初始化展开的一些操作
     */
    public void start() throws Exception {
        final ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("==========>>>>> Tomcat start on port: " + port);

        loadServlet();

        threadPoolExecutor = new ThreadPoolExecutor(1 , 3 , 10 , TimeUnit.SECONDS , new ArrayBlockingQueue<>(16));

        while (true) {
            // 没有请求就阻塞在这里
            final Socket socket = serverSocket.accept();

//            v1_0(socket);
//            v2_0(socket);
//            v3_0(socket);
//            v4_0(socket);
            v4_1(socket);

//            socket.close();
        }
    }

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet() throws Exception {
        final Document document = new SAXReader().read(this.getClass().getClassLoader().getResourceAsStream("web.xml"));

        final List<Node> servlets = document.selectNodes("//servlet");

        for (Node s : servlets) {
            final Element element = (Element) s;
            final String servletName = element.elementText("servlet-name");
            final String servletClass = element.elementText("servlet-class");
            final String urlPattern = document.selectSingleNode("//servlet-mapping[servlet-name='" + servletName + "']/url-pattern").getText();

            final Servlet servlet = (Servlet) Class.forName(servletClass).getConstructor().newInstance();

            servletMap.put(urlPattern , servlet);
        }

    }

    /**
     * Tomcat 1.0版本
     * 需求：浏览器请求http://localhost:8080，返回一个固定的字符串到页面
     *
     * @throws IOException
     */
    private final void v1_0(Socket socket) throws IOException {
        // 使用输出流向外输出信息
        final OutputStream os = socket.getOutputStream();
        final String data = "Hello Tomcat";
        final String response = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
        os.write(response.getBytes());
    }

    /**
     * Tomcat 2.0版本
     * 需求：封装Request和Response对象，返回html静态资源文件
     */
    private final void v2_0(Socket socket) throws IOException {
        // 使用输入流获传输信息
        final InputStream inputStream = socket.getInputStream();

        final Request request = new Request(inputStream);
        final Response response = new Response(socket.getOutputStream());

        response.outputHtml(request.getUrl());
    }

    /**
     * Tomcat 3.0版本
     * 需求：封装Servlet
     *
     * @param socket
     * @throws Exception
     */
    private final void v3_0(Socket socket) throws Exception {
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

    /**
     * Tomcat 4.0版本
     * 需求：多线程
     *
     * @param socket
     */
    private final void v4_0(Socket socket) {
        new Thread(new RequestProcessor(socket , servletMap)).start();
    }

    /**
     * Tomcat 4.0版本
     * 需求：多线程，使用线程池
     *
     * @param socket
     */
    private final void v4_1(Socket socket) {
        threadPoolExecutor.execute(new RequestProcessor(socket , servletMap));
    }

    /**
     * 自定义Tomcat主类入口
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        final Bootstrap bootstrap = new Bootstrap();

        bootstrap.start();
    }

}
