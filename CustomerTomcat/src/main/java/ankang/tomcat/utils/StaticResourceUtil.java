package ankang.tomcat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-15
 */
public class StaticResourceUtil {

    /**
     * 获取静态资源绝对路径
     *
     * @param path
     * @return
     */
    public static String getAbsolutePath(String path) {
        final String dirPath = StaticResourceUtil.class.getResource("/").getPath();
        return dirPath + System.getProperty("file.separator") + path;
    }

    /**
     * 输出静态资源文件
     *
     * @param inputStream
     * @param outputStream
     * @return
     * @throws IOException
     */
    public static void outputStaticResource(InputStream inputStream , OutputStream outputStream) throws IOException {
        final int contentLength = inputStream.available();
        final String header = HttpProtocolUtil.getHttpHeader200(contentLength);

        // 输出报头
        outputStream.write(header.getBytes());

        // 输出内容
        inputStream.transferTo(outputStream);
    }

    public static void output404(OutputStream outputStream) throws IOException {
        final String str404 = HttpProtocolUtil.getHttpHeader404();
        outputStream.write(str404.getBytes());
    }

}
