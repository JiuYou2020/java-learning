package cn.learning.rabbitmq.orderly.config;

import cn.learning.rabbitmq.orderly.message.DemoMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
        public Queue demoQueue0() {
            return new Queue(DemoMessage.QUEUE_0);
        }
        @Bean
        public Queue demoQueue1() {
            return new Queue(DemoMessage.QUEUE_1);
        }
        @Bean
        public Queue demoQueue2() {
            return new Queue(DemoMessage.QUEUE_2);
        }
        @Bean
        public Queue demoQueue3() {
            return new Queue(DemoMessage.QUEUE_3);
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demoExchange() {
            return new DirectExchange(DemoMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        @Bean
        public Binding demoBinding0() {
            return BindingBuilder.bind(demoQueue0()).to(demoExchange()).with("0");
        }
        @Bean
        public Binding demoBinding1() {
            return BindingBuilder.bind(demoQueue1()).to(demoExchange()).with("1");
        }
        @Bean
        public Binding demoBinding2() {
            return BindingBuilder.bind(demoQueue2()).to(demoExchange()).with("2");
        }
        @Bean
        public Binding demoBinding3() {
            return BindingBuilder.bind(demoQueue3()).to(demoExchange()).with("3");
        }

    }

}
