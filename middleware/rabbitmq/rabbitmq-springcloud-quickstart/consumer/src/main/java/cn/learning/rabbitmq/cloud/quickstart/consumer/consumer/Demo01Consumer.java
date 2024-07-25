package cn.learning.rabbitmq.cloud.quickstart.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class Demo01Consumer {
    /**
     * bean的名称需要与配置文件中`spring.cloud.stream.bindings.demo01-in-0`对应
     *
     * @return Consumer<String>
     */
    @Bean
    public Consumer<String> demo01() {
        return message -> {
            log.info("[demo01][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        };
    }
}
