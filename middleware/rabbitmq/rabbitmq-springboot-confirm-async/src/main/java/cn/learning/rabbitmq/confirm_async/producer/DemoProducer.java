package cn.learning.rabbitmq.confirm_async.producer;

import cn.learning.rabbitmq.confirm_async.message.DemoMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 DemoMessage 消息
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(DemoMessage.EXCHANGE, DemoMessage.ROUTING_KEY, message);
    }

    public void syncSendReturn(Integer id) {
        // 创建 DemoMessage 消息
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(DemoMessage.EXCHANGE, "error", message);
    }

}
