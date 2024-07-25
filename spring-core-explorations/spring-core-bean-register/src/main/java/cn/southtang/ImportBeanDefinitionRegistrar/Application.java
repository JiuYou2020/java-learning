package cn.southtang.ImportBeanDefinitionRegistrar;

import cn.southtang.Ext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ExtScan(defaultName = "pq先生")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Ext ext = context.getBean(Ext.class);
        // 输出：pq先生
        System.out.println(ext.getName());
        ext.print();
    }

}