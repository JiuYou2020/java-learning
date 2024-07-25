package cn.learning.rabbitmq.springboot.quickstart.message;

import lombok.Data;

import java.io.Serializable;

/**
 * Direct Exchange
 */
@Data
public class Demo01Message implements Serializable {
    public static final String QUEUE = "QUEUE_DEMO_01";

    public static final String EXCHANGE = "EXCHANGE_DEMO_01";

    public static final String ROUTING_KEY = "ROUTING_KEY_01";

    /**
     * 编号
     */
    private Integer id;
}
