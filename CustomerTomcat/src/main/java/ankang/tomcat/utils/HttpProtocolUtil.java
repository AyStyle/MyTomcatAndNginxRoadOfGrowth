package ankang.tomcat.utils;

/**
 * http协议工具类，主要是提供响应头信息，这里我们只提供200和404的情况
 *
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-14
 */
public class HttpProtocolUtil {

    /**
     * 为响应码为200提供请求头信息
     *
     * @return
     */
    public static String getHttpHeader200(long contentLength) {
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + contentLength + " \n" +
                "\n";
    }

    /**
     * 为响应码为404提供请求头信息，此处也包含数据内容
     *
     * @return
     */
    public static String getHttpHeader404() {
        final String str404 = "<h1>404 not found</>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + " \n" +
                "\n" + str404;
    }

}
