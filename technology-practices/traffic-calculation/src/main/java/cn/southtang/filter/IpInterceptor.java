package cn.southtang.filter;

import cn.southtang.util.IpUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiuyou2020
 */
@Component
public class IpInterceptor implements HandlerInterceptor {
    private final RedisTemplate redisTemplate;

    /**
     * 通过构造方法的方式注入 RedisTemplate，避免拦截器的实例化过程中 RedisTemplate 未初始化的问题，因为拦截器会在 Spring 容器初始化之前就实例化
     *
     * @param redisTemplateProvider RedisTemplate 的提供者
     */
    @Autowired
    public IpInterceptor(ObjectProvider<RedisTemplate<String, Object>> redisTemplateProvider) {
        this.redisTemplate = redisTemplateProvider.getIfAvailable();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 同样是为了解决上述问题，这是另一种方法
        //        if (redisTemplate == null) {
        //            // 延迟加载 RedisTemplate
        //            redisTemplate = SpringContext.getBean(RedisTemplate.class);
        //        }
        String ip = IpUtil.getIpAddress(request);
        System.out.println("ip: " + ip);
        HyperLogLogOperations hyperLogLog = redisTemplate.opsForHyperLogLog();
        hyperLogLog.add("total_access_today", ip);
        return true;
    }
}