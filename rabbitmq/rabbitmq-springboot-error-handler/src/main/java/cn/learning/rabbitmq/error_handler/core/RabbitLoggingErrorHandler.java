package cn.learning.rabbitmq.error_handler.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * - 在构造方法中，把自己设置到 SimpleRabbitListenerContainerFactory 中，作为其 ErrorHandler 异常处理器。
 * <p>
 * - 在 `#handleError(...)` 方法中，打印错误日志。当然，具体怎么处理，胖友可以根据自己的需要哈。
 */
@Component
@Slf4j
public class RabbitLoggingErrorHandler implements ErrorHandler {


    public RabbitLoggingErrorHandler(SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory) {
        rabbitListenerContainerFactory.setErrorHandler(this);
    }

    @Override
    public void handleError(Throwable t) {
        log.error("[handleError][发生异常]]", t);
    }

}
