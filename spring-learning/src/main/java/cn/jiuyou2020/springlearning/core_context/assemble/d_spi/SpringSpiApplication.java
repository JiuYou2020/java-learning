package cn.jiuyou2020.springlearning.core_context.assemble.d_spi;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * @author: jiuyou2020
 * @description: spring的spi机制，它会加载META-INF/spring.factories文件中定义的类，而且可直接把预先定义好的全限定名提出取出来
 */
public class SpringSpiApplication {
    public static void main(String[] args) {
        ClassLoader classLoader = SpringSpiApplication.class.getClassLoader();
        List<DemoDao> demoDaos = SpringFactoriesLoader.loadFactories(DemoDao.class, classLoader);
        demoDaos.forEach(System.out::println);
        System.out.println("--------------------");
        List<String> daoClassNames = SpringFactoriesLoader.loadFactoryNames(DemoDao.class, classLoader);
        daoClassNames.forEach(System.out::println);

        System.out.println("--------------------");
        // 上面的方式ide会提示已弃用，推荐使用下面的方式，当然，下面的foreach可以换成steams流处理
        SpringFactoriesLoader.ArgumentResolver argumentResolver = SpringFactoriesLoader.ArgumentResolver.none();
        SpringFactoriesLoader.FailureHandler failureHandler = SpringFactoriesLoader.FailureHandler.throwing();
        SpringFactoriesLoader loader = SpringFactoriesLoader.forDefaultResourceLocation(classLoader);
        List<DemoDao> factories = loader.load(DemoDao.class, argumentResolver, failureHandler);
        factories.forEach(fac -> {
            System.out.println(fac.getClass().getName());
        });
    }
}
