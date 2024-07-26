// WebConfig.java
package cn.southtang.config;

import cn.southtang.filter.IpInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ObjectProvider<RedisTemplate<String, Object>> redisTemplateProvider;

    public WebConfig(ObjectProvider<RedisTemplate<String, Object>> redisTemplateProvider) {
        this.redisTemplateProvider = redisTemplateProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IpInterceptor(redisTemplateProvider)).addPathPatterns("/**");
    }
}