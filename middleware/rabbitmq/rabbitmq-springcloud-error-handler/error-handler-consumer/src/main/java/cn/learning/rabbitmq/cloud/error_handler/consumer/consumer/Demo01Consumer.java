package cn.learning.rabbitmq.cloud.error_handler.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.support.ErrorMessage;
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
            // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
            throw new RuntimeException("我就是故意抛出一个异常");
        };
    }

    @ServiceActivator(inputChannel = "DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01.errors")
    public void handleError(ErrorMessage errorMessage) {
        log.error("[handleError][payload：{}]", errorMessage.getPayload().getMessage());
        log.error("[handleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[handleError][headers：{}]", errorMessage.getHeaders());
    }

    /**
     * // 指定输入通道名字，这里假设是 "errorChannel"
     *
     * @param errorMessage errorMessage
     */
    @ServiceActivator(inputChannel = IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public void globalHandleError(ErrorMessage errorMessage) {
        log.error("[globalHandleError][payload：{}]", errorMessage.getPayload().getMessage());
        log.error("[globalHandleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[globalHandleError][headers：{}]", errorMessage.getHeaders());
    }
}
