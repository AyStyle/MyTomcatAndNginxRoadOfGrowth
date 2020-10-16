package ankang.tomcat.server;

import ankang.tomcat.http.Request;
import ankang.tomcat.http.Response;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-15
 */
public interface Servlet {

    void init() throws Exception;

    void destory() throws Exception;

    void service(Request request , Response response) throws Exception;


}
