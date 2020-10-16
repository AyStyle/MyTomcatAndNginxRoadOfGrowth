package ankang.tomcat.server;

import ankang.tomcat.http.Request;
import ankang.tomcat.http.Response;
import ankang.tomcat.utils.HttpProtocolUtil;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-15
 */
public class MyServlet extends HttpServlet {

    @Override
    public void doGet(Request request , Response response) throws Exception {
        Thread.sleep(60 * 1000);
        final String context = "<h1>MyServlet Get</h1>";

        response.outputStr(HttpProtocolUtil.getHttpHeader200(context.getBytes().length) + context);
    }

    @Override
    public void doPost(Request request , Response response) throws Exception {
        final String context = "<h1>MyServlet Post</h1>";

        response.outputStr(HttpProtocolUtil.getHttpHeader200(context.getBytes().length) + context);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }

}
