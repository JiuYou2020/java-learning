package cn.learning.rabbitmq.error_handler.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 * - 在类上，添加 `@Component` 注解，并设置其 Bean 名为 `"rabbitListenerErrorHandler"` 。稍后，我们会使用到该 Bean 名字。
 * <p>
 * - 在 `#handleError(...)` 方法中，我们先打印异常日志，并继续抛出 ListenerExecutionFailedException 异常。要注意，如果此时我们不继续抛出异常，而是 `return` 结果，意味着 Consumer 消息成功。如果我们结合一起使用的时候，一定要继续抛出该异常，否则消费重试机制将失效。
 */
@Component("rabbitListenerErrorHandler")
@Slf4j
public class RabbitListenerErrorHandlerImpl implements RabbitListenerErrorHandler {


    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message,
                              ListenerExecutionFailedException exception) {
        // 打印异常日志
        log.error("[handleError][amqpMessage:[{}] message:[{}]]", amqpMessage, message, exception);

        // 直接继续抛出异常
        throw exception;
    }

}
