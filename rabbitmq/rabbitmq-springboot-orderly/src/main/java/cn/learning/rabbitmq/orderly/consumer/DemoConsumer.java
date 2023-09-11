package cn.learning.rabbitmq.orderly.consumer;

import cn.learning.rabbitmq.orderly.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * DemoMessage
 * <p>
 * - 为了实现每个DemoMessage子DemoMessage Queue 能够被每个 Consumer DemoMessage串行DemoMessage消费，从而实现基于DemoMessage子DemoMessage Queue 的DemoMessage并行DemoMessage的DemoMessage严格DemoMessage消费DemoMessage顺序DemoMessage消息，我们只好在类上添了四个 `@RabbitListener` 注解，每个对应一个DemoMessage子DemoMessage Queue 。
 * <p>
 * - 如果胖友使用一个 `@RabbitListener` 注解，并添加四个DemoMessage子DemoMessage Queue ，然后设置 `concurrency = 4` 时，实际是并发四个线程，消费四个DemoMessage子DemoMessage Queue 的消息，无法保证DemoMessage严格DemoMessage消费DemoMessage顺序DemoMessage消息。
 */
@Component
@RabbitListener(queues = DemoMessage.QUEUE_0)
@RabbitListener(queues = DemoMessage.QUEUE_1)
@RabbitListener(queues = DemoMessage.QUEUE_2)
@RabbitListener(queues = DemoMessage.QUEUE_3)
@Slf4j
public class DemoConsumer {


    @RabbitHandler(isDefault = true)
    public void onMessage(Message<DemoMessage> message) {
        log.info("[onMessage][线程编号:{} Queue:{} 消息编号：{}]", Thread.currentThread().getId(), getQueue(message),
                message.getPayload().getId());
    }

    private static String getQueue(Message<DemoMessage> message) {
        return message.getHeaders().get("amqp_consumerQueue", String.class);
    }

}
