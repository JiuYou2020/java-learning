package cn.learning.rabbitmq.message_model.producer;

import cn.learning.rabbitmq.message_model.message.BroadcastMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BroadcastProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 BroadcastMessage 消息
        BroadcastMessage message = new BroadcastMessage();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(BroadcastMessage.EXCHANGE, null, message);
    }

}
