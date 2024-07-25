package cn.southtang.b_profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // 为ApplicationContext的环境设置正在激活的Profile
        ctx.getEnvironment().setActiveProfiles("city");
        ctx.register(TavernConfiguration.class);
        // 刷新容器
        ctx.refresh();
        Stream.of(ctx.getBeanDefinitionNames()).forEach(System.out::println);

        //或者使用虚拟机参数：-Dspring.profiles.active=city，便可以使用下面的方式
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TavernConfiguration.class);
//        Stream.of(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}