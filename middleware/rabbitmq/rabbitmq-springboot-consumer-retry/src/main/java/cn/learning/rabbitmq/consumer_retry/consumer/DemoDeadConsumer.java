package cn.learning.rabbitmq.consumer_retry.consumer;

import cn.learning.rabbitmq.consumer_retry.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = DemoMessage.DEAD_QUEUE)
public class DemoDeadConsumer {
    @RabbitHandler
    public void onMessage(DemoMessage message) {
        log.info("[onMessage][【死信队列】线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
