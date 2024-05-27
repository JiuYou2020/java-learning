package cn.jiuyou2020.springlearning.context.custom_registered_spring_beans.BeanDefinitionRegistryPostProcessor;

import cn.jiuyou2020.springlearning.context.custom_registered_spring_beans.Ext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Ext ext = context.getBean(Ext.class);
        System.out.println(ext.getName()); // 输出null
    }

}