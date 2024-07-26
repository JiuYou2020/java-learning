package cn.southtang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JiuYou2020
 * @date 2024/07/25
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/visit")
    public String visit() {
        return "visit success";
    }

    @GetMapping("/get")
    public String get() {
        HyperLogLogOperations<String, Object> hyperLogLog = redisTemplate.opsForHyperLogLog();
        Long size = hyperLogLog.size("total_access_today");
        System.out.println("total_access_today: " + size);
        return size.toString();
    }
}
