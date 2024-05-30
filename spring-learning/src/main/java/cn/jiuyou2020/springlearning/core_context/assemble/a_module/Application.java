package cn.jiuyou2020.springlearning.core_context.assemble.a_module;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author: jiuyou2020
 * @description:
 */
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TavernConfiguration.class);
//        Boss boss = ctx.getBean(Boss.class);
//        System.out.println(boss);
//        System.out.println("==================================");
        Stream.of(ctx.getBeanDefinitionNames()).forEach(System.out::println);
        System.out.println("==================================");
        Map<String, Bartender> bartenders = ctx.getBeansOfType(Bartender.class);
        bartenders.forEach((name, bartender) -> System.out.println(name + " : " + bartender));
    }
}
