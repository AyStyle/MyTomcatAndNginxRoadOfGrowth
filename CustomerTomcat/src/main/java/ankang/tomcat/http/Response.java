package ankang.tomcat.http;

import ankang.tomcat.utils.StaticResourceUtil;

import java.io.*;

/**
 * 封装Response对象，需要依赖于OutputStream
 * 该对象需要提供核心方法，输出html
 *
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-15
 */
public class Response {


    private OutputStream outputStream;

    public Response() {

    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * 响应返回html
     *
     * @param path url，随后要根据url来获取到静态资源的绝对路径，
     *             进一步根据绝对路径读取该静态资源文件，最终通过输出流输出
     */
    public void outputHtml(String path) throws IOException {
        // 获取静态资源的绝对路径
        final String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);

        // 输出静态资源文件
        File file = new File(absoluteResourcePath);
        if (file.isFile()) {
            try (final InputStream inputStream = new FileInputStream(file)) {
                StaticResourceUtil.outputStaticResource(inputStream , outputStream);
            }
        } else {
            StaticResourceUtil.output404(outputStream);
        }
    }

    /**
     * 输出指定字符串
     *
     * @param content
     */
    public void outputStr(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

}
