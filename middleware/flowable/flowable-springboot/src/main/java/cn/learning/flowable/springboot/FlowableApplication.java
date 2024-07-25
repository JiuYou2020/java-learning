package cn.learning.flowable.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2023/9/8
 * <br>
 * {@code @Description: }
 */
@SpringBootApplication(proxyBeanMethods = false)
public class FlowableApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }

}