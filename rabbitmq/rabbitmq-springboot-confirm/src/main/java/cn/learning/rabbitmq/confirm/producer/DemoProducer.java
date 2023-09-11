package cn.learning.rabbitmq.confirm.producer;

import cn.learning.rabbitmq.confirm.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 DemoMessage 消息
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.invoke(
                operations -> {
                    // 同步发送消息
                    operations.convertAndSend(DemoMessage.EXCHANGE, DemoMessage.ROUTING_KEY, message);
                    log.info("[doInRabbit][发送消息完成]");
                    // 等待确认
                    operations.waitForConfirms(0); // timeout 参数，如果传递 0 ，表示无限等待
                    log.info("[doInRabbit][等待 Confirm 完成]");
                    return null;
                },
                (deliveryTag, multiple) -> log.info("[handle][Confirm 成功]"),
                (deliveryTag, multiple) -> log.info("[handle][Confirm 失败]"));

    }

}
