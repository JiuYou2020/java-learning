package cn.jiuyou2020.springlearning.core_context.assemble.a_module;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiuyou2020
 * @description: 使用@Import导入项目中现有的一些配置类
 */
@Configuration
public class BartenderConfiguration {
    @Bean
    public Bartender bartender() {
        return new Bartender("张小三");
    }

    @Bean
    public Bartender bartender2() {
        return new Bartender("李小四");
    }
}
