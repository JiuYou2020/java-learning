package cn.learning.rabbitmq.confirm.consumer;

import cn.learning.rabbitmq.confirm.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = DemoMessage.QUEUE)
public class DemoConsumer {


    @RabbitHandler
    public void onMessage(DemoMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
