package cn.southtang.BeanDefinitionRegistryPostProcessor;

import cn.southtang.Ext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Ext ext = context.getBean(Ext.class);
        // 输出null
        System.out.println(ext.getName());
    }

}