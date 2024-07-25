package cn.learning.rabbitmq.timed_message.producer;

import cn.learning.rabbitmq.timed_message.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, Integer delay) {
        // 创建 Demo07Message 消息
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(DemoMessage.EXCHANGE, DemoMessage.ROUTING_KEY, message, new MessagePostProcessor() {

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息的 TTL 过期时间
                if (delay != null && delay > 0) {
                    message.getMessageProperties().setExpiration(String.valueOf(delay)); // Spring-AMQP API 设计有问题，所以传入了 String = =
                }
                return message;
            }

        });
    }
}
