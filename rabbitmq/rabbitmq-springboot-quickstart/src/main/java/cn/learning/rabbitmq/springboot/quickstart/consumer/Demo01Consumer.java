package cn.learning.rabbitmq.springboot.quickstart.consumer;// Demo01Consumer.java

import cn.learning.rabbitmq.springboot.quickstart.message.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Direct Exchange
 */
@Component
@RabbitListener(queues = Demo01Message.QUEUE)
@Slf4j
public class Demo01Consumer {

    @RabbitHandler
    public void onMessage(Demo01Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}