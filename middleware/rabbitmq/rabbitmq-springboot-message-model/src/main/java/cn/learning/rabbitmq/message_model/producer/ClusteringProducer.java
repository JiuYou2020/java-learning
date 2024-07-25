package cn.learning.rabbitmq.message_model.producer;

import cn.learning.rabbitmq.message_model.message.ClusteringMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClusteringProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 ClusteringMessage 消息
        ClusteringMessage message = new ClusteringMessage();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(ClusteringMessage.EXCHANGE, null, message);
    }

}
