package ankang.tomcat.http;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;

/**
 * 把请求信息封装为Request对象（根据InputStream输入流封装）
 *
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-14
 */
public class Request {


    /**
     * 请求方式，例如：GET, POST
     */
    @Getter
    @Setter
    private String method;

    /**
     * 请求url
     */
    @Getter
    @Setter
    private String url;

    /**
     * 输入流，其他属性从输入流中解析出来
     */
    @Getter
    @Setter
    private InputStream inputStream;

    public Request() {

    }

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        // 获取输入长度
        int count = 0;
        while (count == 0) {
            count = this.inputStream.available();
        }

        final byte[] bytes = new byte[count];

        inputStream.read(bytes);
        final String inputStr = new String(bytes);
        final String[] split = inputStr.split(System.getProperty("line.separator"));

        final String requestLine = split[0]; // GET / HTTP/1.1
        final String[] requestInfos = requestLine.split(" ");
        this.method = requestInfos[0];
        this.url = requestInfos[1];

        System.out.println("===========>>>> method：" + this.method);
        System.out.println("===========>>>> url：" + this.url);
    }
}
