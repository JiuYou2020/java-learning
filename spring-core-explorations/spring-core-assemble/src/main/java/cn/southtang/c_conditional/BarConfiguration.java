package cn.southtang.c_conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiuyou2020
 * @description:
 */
@Configuration
public class BarConfiguration {
    @Bean(name = "bbbar")
    @Conditional(ExistBossCondition.class)
    public Bar bbbar() {
        return new Bar();
    }

}
