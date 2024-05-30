package cn.jiuyou2020.springlearning.core_context.assemble.b_profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author: jiuyou2020
 * @description: 使用@Import导入项目中现有的一些配置类
 * <p>
 * 通过@Profile注解，可以指定在不同的环境下，加载不同的配置类
 */
@Configuration
@Profile("city")
public class BartenderConfiguration {
    @Bean(name = "zhangxiaosan")
    public Bartender bartender() {
        return new Bartender("张小三");
    }

    @Bean(name = "lixiaosi")
    public Bartender bartender2() {
        return new Bartender("李小四");
    }
}
