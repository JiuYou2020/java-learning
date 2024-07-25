package cn.southtang.c_conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Application {
    public static void main(String[] args) {
        // 使用@condition注解
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TavernConfiguration.class);
        Stream.of(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}