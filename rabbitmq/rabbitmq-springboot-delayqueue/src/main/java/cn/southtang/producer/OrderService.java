package cn.southtang.producer;

import cn.southtang.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void createOrder(String order) {
        // 订单创建逻辑
        // ...
        
        // 发送延时消息
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-delay", 6000); // 延时 60 秒

        rabbitTemplate.convertAndSend(
                RabbitConfig.ORDER_DELAY_EXCHANGE,
                RabbitConfig.ORDER_DELAY_ROUTING_KEY,
                order,
                message -> {
                    message.getMessageProperties().getHeaders().putAll(headers);
                    return message;
                }
        );
    }
}
