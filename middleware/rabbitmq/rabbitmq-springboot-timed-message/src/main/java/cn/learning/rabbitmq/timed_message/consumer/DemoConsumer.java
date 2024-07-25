package cn.learning.rabbitmq.timed_message.consumer;

import cn.learning.rabbitmq.timed_message.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = DemoMessage.DELAY_QUEUE)
public class DemoConsumer {


    @RabbitHandler
    public void onMessage(DemoMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
