package cn.jiuyou2020.rpc;

import cn.jiuyou2020.proxy.ProxyRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ProxyRegistrar.class)
public class ClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ClientApplication.class, args);
        Object stockApi = applicationContext.getBean("demo");
        System.out.println(stockApi);
    }

}
