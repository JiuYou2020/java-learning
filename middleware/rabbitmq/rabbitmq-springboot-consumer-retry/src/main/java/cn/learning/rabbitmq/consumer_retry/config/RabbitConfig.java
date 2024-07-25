package cn.learning.rabbitmq.consumer_retry.config;

import cn.learning.rabbitmq.consumer_retry.message.DemoMessage;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demoQueue() {
            return QueueBuilder.durable(DemoMessage.QUEUE) // durable: 是否持久化
                    .exclusive() // exclusive: 是否排它
                    .autoDelete() // autoDelete: 是否自动删除
                    .deadLetterExchange(DemoMessage.EXCHANGE) // 指定当前 Queue 的 DLX 为 DLX_QUEUE_EXCHANGE
                    .deadLetterRoutingKey(DemoMessage.DEAD_ROUTING_KEY) // 指定当前 Queue 的 DLK 为 DLX_QUEUE_ROUTING_KEY
                    .build();
        }

        // 创建 Dead Queue
        @Bean
        public Queue demoDeadQueue() {
            return new Queue(DemoMessage.DEAD_QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demoExchange() {
            return new DirectExchange(DemoMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：DemoMessage.EXCHANGE
        // Routing key：DemoMessage.ROUTING_KEY
        // Queue：DemoMessage.QUEUE
        @Bean
        public Binding demoBinding() {
            return BindingBuilder.bind(demoQueue()).to(demoExchange()).with(DemoMessage.ROUTING_KEY);
        }

        // 创建 Dead Binding
        // Exchange：DemoMessage.EXCHANGE
        // Routing key：DemoMessage.DEAD_ROUTING_KEY
        // Queue：DemoMessage.DEAD_QUEUE
        @Bean
        public Binding demoDeadBinding() {
            return BindingBuilder.bind(demoDeadQueue()).to(demoExchange()).with(DemoMessage.DEAD_ROUTING_KEY);
        }

    }

}
