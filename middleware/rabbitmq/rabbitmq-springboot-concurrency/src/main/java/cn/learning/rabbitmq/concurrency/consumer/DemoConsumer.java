package cn.learning.rabbitmq.concurrency.consumer;

import cn.learning.rabbitmq.concurrency.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = DemoMessage.QUEUE,
        concurrency = "2")
@Slf4j
public class DemoConsumer {


    @RabbitHandler
    public void onMessage(DemoMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
