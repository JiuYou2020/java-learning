package cn.southtang.d_spi;

import java.util.ServiceLoader;

/**
 * @author: jiuyou2020
 * @description: jdk原生的spi机制，spi（服务提供方接口）是一种服务发现机制，它通过在ClassPath路径下的META-INF/services文件夹查找文件，自动加载文件里所定义的类。
 */
public class JdkSpiApplication {
    public static void main(String[] args) {
        ServiceLoader<DemoDao> serviceLoader = ServiceLoader.load(DemoDao.class);
        serviceLoader.iterator().forEachRemaining(System.out::println);
    }
}
