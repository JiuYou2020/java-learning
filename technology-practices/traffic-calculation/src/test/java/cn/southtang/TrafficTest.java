package cn.southtang;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

/**
 * @author JiuYou2020
 * @date 2024/07/25
 */
@SpringBootTest
public class TrafficTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        SetOperations<String, Object> ops = redisTemplate.opsForSet();
        ops.add("total_access_today", "1234");

    }
}
