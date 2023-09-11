package cn.learning.rabbitmq.consumer_retry.message;

import java.io.Serializable;

public class DemoMessage implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO"; // 正常队列
    public static final String DEAD_QUEUE = "DEAD_QUEUE_DEMO"; // 死信队列

    public static final String EXCHANGE = "EXCHANGE_DEMO";

    public static final String ROUTING_KEY = "ROUTING_KEY"; // 正常路由键
    public static final String DEAD_ROUTING_KEY = "DEAD_ROUTING_KEY"; // 死信路由键


    /**
     * 编号
     */
    private Integer id;

    public DemoMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DemoMessage{" +
                "id=" + id +
                '}';
    }

}
