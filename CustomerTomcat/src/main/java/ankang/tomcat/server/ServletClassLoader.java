package ankang.tomcat.server;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;

/**
 * @author: ankang
 * @email: dreedisgood@qq.com
 * @create: 2020-10-20
 */
public class ServletClassLoader extends ClassLoader {
    /**
     * 系统文件分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private final String webappsPath;

    public ServletClassLoader(String webappPath) {
        super();
        this.webappsPath = webappPath;
    }

    @Override
    protected Class<?> loadClass(String name , boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            if (name.contains("/")) {
                return findClass(name);
            }
            return super.loadClass(name , resolve);
        }
    }

    /**
     * 加载不同项目，各自Servlet类
     *
     * @param name 各自项目Servlet的类目，格式：urlPattern/package.class，eg：/demo/a.b.c.className，注意：所有的project都必须放在webapps目录下
     * @return 返回不同项目，各自的Servlet类
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final int i = name.lastIndexOf("/");

        // urlPattern：/demo -> demo
        final String projectPath = name.substring(1 , i);
        // 类名：a.b.c.className
        final String className = name.substring(i + 1);
        // 类的绝对路径
        final String classPath = webappsPath + FILE_SEPARATOR + projectPath + FILE_SEPARATOR + className.replaceAll("\\." , Matcher.quoteReplacement(FILE_SEPARATOR)) + ".class";

        // 加载类对象
        try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(classPath))) {
            final byte[] classBytes = bis.readAllBytes();

            return defineClass(className , classBytes , 0 , classBytes.length);
        } catch (FileNotFoundException e) {
            final String msg = String.format("%s can't be find in %s." , className , classPath);
            throw new ClassNotFoundException(msg , e);
        } catch (IOException e) {
            final String msg = String.format("%s can't be load in %s." , className , classPath);
            throw new ClassNotFoundException(msg , e);
        }
    }

}
