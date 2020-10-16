package ankang.tomcat.server;

import ankang.tomcat.http.Request;
import ankang.tomcat.http.Response;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-15
 */
public abstract class HttpServlet implements Servlet{

    @Override
    public void service(Request request , Response response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }

    public abstract void doGet(Request request,Response response) throws Exception;

    public abstract void doPost(Request request,Response response) throws Exception;

}
