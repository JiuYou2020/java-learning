package cn.southtang.consumer;

import cn.southtang.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

    @RabbitListener(queues = RabbitConfig.ORDER_DELAY_QUEUE)
    public void processOrder(String order) {
        // 处理过期订单逻辑
        // ...
        log.info("[processOrder][延时消息队列] [{}]", order);
    }
}
